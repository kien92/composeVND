package vn.mtk.compose.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vn.mtk.compose.common.Constants
import vn.mtk.compose.common.getTodayTimeStamp
import vn.mtk.compose.domain.model.ResultData
import vn.mtk.compose.domain.usecase.DChartHistoryUseCase
import vn.mtk.compose.presentation.mapper.toUiModel
import vn.mtk.compose.presentation.model.DCharPriceUi
import vn.mtk.compose.presentation.ui.main.DCharMainState

class MainViewModel(
    private val getDChartHistoryUseCase: DChartHistoryUseCase
) : ViewModel() {
    /**
     * Biến lưu trạng thái view để hiển thị dữ liệu lên màn hình
     */
    private val _state = MutableStateFlow<DCharMainState>(DCharMainState.Loading)
    val state: StateFlow<DCharMainState> = _state

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    private val _currentSelectedItem = MutableStateFlow<DCharPriceUi?>(null)
    val currentSelectedItem: StateFlow<DCharPriceUi?> = _currentSelectedItem


    private var isLoadingMore = false
    private var current: Long = getTodayTimeStamp()

    /**
     * Biến kiểm tra trạng thái khi đang load
     */
    private var _isLoaded = false
    val isLoaded get() = _isLoaded

    private val pageSize = 10 // tối đa số bản ghi cho 1 page khi gọi hoặc xử lý phân trang
    private val seconds = 86400L //tổng số giây một ngày

    /**
    INIT  giữ liệu
     */
    fun loadInitial() = viewModelScope.launch {
        if (_isLoaded) return@launch
        _isRefreshing.value = true
        current = getTodayTimeStamp()

        fetchChartData(from = getFrom(), to = current) { result ->
            _state.value = result
            _isLoaded = result is DCharMainState.Success
        }

        _isRefreshing.value = false
    }

    /**
     * REFRESH giữ liệu
     */
    fun refreshData() {
        _isLoaded = false
        loadInitial()
    }

    /**
    LOAD MORE thêm dũ liệu
     */
    fun loadMoreIfNeeded(lastVisibleIndex: Int, totalItems: Int) {
        if (_state.value !is DCharMainState.Success) return
        if (isLoadingMore) return
        if (lastVisibleIndex < totalItems - 1) return

        isLoadingMore = true

        viewModelScope.launch {
            val currentState = _state.value as? DCharMainState.Success ?: return@launch
            val from = getFrom()
            val to = current

            fetchChartData(from, to) { result ->
                _state.value = when (result) {
                    is DCharMainState.Success -> {
                        val merged = (currentState.data + result.data).distinctBy { it.timestamp }
                        current = from
                        DCharMainState.Success(merged)
                    }

                    else -> result
                }
                isLoadingMore = false
            }
        }
    }

    /**
       CHỌN ITEM
     */
    fun selectItem(item: DCharPriceUi) {
        _currentSelectedItem.value = item
    }

    private fun getFrom(): Long = current - (pageSize * seconds)

    /**
     * Function thực hiện callApi
     */
    private suspend fun fetchChartData(
        from: Long, to: Long, onResult: (DCharMainState) -> Unit
    ) {
        val result = withContext(Dispatchers.IO) {
            getDChartHistoryUseCase(Constants.D, Constants.UNIT, from, to)
        }

        val newState = when (result) {
            is ResultData.Success -> DCharMainState.Success(result.data.map { it.toUiModel() })
            is ResultData.Error -> DCharMainState.Error(result.errorType)
        }

        delay(Constants.DELAY_LOAD_MORE)
        onResult(newState)
    }
}