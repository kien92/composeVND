package vn.mtk.compose.presentation.ui.main

import vn.mtk.compose.domain.model.DCharPrice
import vn.mtk.compose.domain.model.ErrorType
import vn.mtk.compose.presentation.model.DCharPriceUi

sealed class MainState {
    data object Loading : MainState()
    data class Success(
        val data: List<DCharPriceUi>,
        val isLoadingMore: Boolean = false,
        val hasMoreData: Boolean = true
    ) : MainState()
    data class Error(val errorType: ErrorType) : MainState()
}
