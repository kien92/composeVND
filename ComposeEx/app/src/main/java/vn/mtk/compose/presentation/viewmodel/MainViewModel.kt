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
import vn.mtk.compose.domain.model.DCharPrice
import vn.mtk.compose.domain.model.ResultData
import vn.mtk.compose.domain.usecase.DChartHistoryUseCase
import vn.mtk.compose.presentation.mapper.toUiModel
import vn.mtk.compose.presentation.model.DCharPriceUi
import vn.mtk.compose.presentation.ui.main.MainState

class MainViewModel(
    private val getDChartHistoryUseCase: DChartHistoryUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<MainState>(MainState.Loading)
    val state: StateFlow<MainState> = _state

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    private var current: Long = getTodayTimeStamp()
    private val pageSize = 10
    private val seconds = 86400L

    private var _isLoaded = false
    val isLoaded get() = _isLoaded

    fun loadInitial() = viewModelScope.launch {
        if (_isLoaded) return@launch
        _isRefreshing.value = true
        current = getTodayTimeStamp()

        val (from, to) = getFromToRange()
        val result = withContext(Dispatchers.IO) {
            getDChartHistoryUseCase(Constants.D, Constants.UNIT, from, to)
        }

        _state.value = when (result) {
            is ResultData.Success -> {
                current = from
                MainState.Success(result.data.map { it.toUiModel() })
            }

            is ResultData.Error -> MainState.Error(result.errorType)
        }

        delay(Constants.DELAY_LOAD_MORE)
        _isRefreshing.value = false
        _isLoaded = true
    }

    fun reloadForce() {
        _isLoaded = false
        loadInitial()
    }

    fun loadMore() = viewModelScope.launch {
        val currentState = _state.value
        if (currentState !is MainState.Success) return@launch

        val (from, to) = getFromToRange()
        val result:ResultData<List<DCharPrice>> = getDChartHistoryUseCase("1D", "VND", from, to)
        _state.value = when (result) {
            is ResultData.Success -> {
                val mapperUIData = result.data.map { it.toUiModel() }
                val updated = (currentState.data + mapperUIData).distinctBy { it.timestamp }
                current = from
                MainState.Success(updated)
            }

            is ResultData.Error -> MainState.Error(result.errorType)
        }
    }

    private fun getFromToRange(): Pair<Long, Long> {
        val from = current - pageSize * seconds
        val to = current
        return Pair(from, to)
    }

    private val _currentSelectedItem = MutableStateFlow<DCharPriceUi?>(null)
    val currentSelectedItem: StateFlow<DCharPriceUi?> = _currentSelectedItem

    fun selectItem(item: DCharPriceUi) {
        _currentSelectedItem.value = item
    }

}