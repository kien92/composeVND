package vn.mtk.compose.presentation.ui.main

import vn.mtk.compose.domain.model.ErrorType
import vn.mtk.compose.presentation.model.DCharPriceUi

sealed class DCharMainState {
    data object Loading : DCharMainState()
    data class Success(
        val data: List<DCharPriceUi>,
        val isLoadingMore: Boolean = false,
        val hasMoreData: Boolean = true
    ) : DCharMainState()
    data class Error(val errorType: ErrorType) : DCharMainState()
}
