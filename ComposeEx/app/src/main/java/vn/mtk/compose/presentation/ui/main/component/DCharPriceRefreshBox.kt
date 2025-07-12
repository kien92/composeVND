package vn.mtk.compose.presentation.ui.main.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import vn.mtk.compose.R
import vn.mtk.compose.presentation.model.DCharPriceUi
import vn.mtk.compose.presentation.ui.theme.AppDimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DCharPriceRefreshBox(
    data: List<DCharPriceUi>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onLoadMore: (lastVisibleIndex: Int, totalItems: Int) -> Unit,
    onItemClick: (DCharPriceUi) -> Unit
) {
    val pullToRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        state = pullToRefreshState,
        modifier = Modifier.fillMaxSize(),
        onRefresh = onRefresh
    ) {
        if (data.isEmpty()) Text(
            stringResource(R.string.error_no_data),
            color = Color.White,
            modifier = Modifier
                .padding(AppDimens.ScreenSizeLarge)
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                ),
            textAlign = TextAlign.Center
        )
        else DCharPriceList(data, onLoadMore, onItemClick)
    }
}
