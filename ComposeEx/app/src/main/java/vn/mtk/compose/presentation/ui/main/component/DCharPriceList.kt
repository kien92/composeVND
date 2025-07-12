package vn.mtk.compose.presentation.ui.main.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import vn.mtk.compose.presentation.model.DCharPriceUi

@Composable
fun DCharPriceList(
    data: List<DCharPriceUi>,
    onLoadMore: (lastVisibleIndex: Int, totalItems: Int) -> Unit,
    onItemClick: (DCharPriceUi) -> Unit
) {
    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo }
            .collect { layoutInfo ->
                val lastVisibleIndex =
                    layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: return@collect
                val totalItems = layoutInfo.totalItemsCount
                onLoadMore(lastVisibleIndex, totalItems)
            }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = listState
    ) {
        itemsIndexed(data) { index, record ->
            val prev = data.getOrNull(index + 1)
            DCharPriceItemList(
                index = index,
                currentRecord = record,
                previousRecord = prev,
                clickable = { onItemClick.invoke(record) }
            )
        }
    }
}