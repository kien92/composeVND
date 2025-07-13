package vn.mtk.compose.presentation.ui.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import vn.mtk.compose.R
import vn.mtk.compose.common.formatTimeStamp
import vn.mtk.compose.common.formatVolume
import vn.mtk.compose.presentation.ui.component.DChartDetailItem
import vn.mtk.compose.presentation.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel: MainViewModel,
    navController: NavHostController
) {
    val currentSelectedItem by viewModel.currentSelectedItem.collectAsState()
    val detailList = buildDetailList(currentSelectedItem)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.stock_detail_title)) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors().copy(
                    containerColor = colorResource(R.color.dart_background),
                    titleContentColor = Color.Black
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.des_img),
                            tint = Color.Black
                        )
                    }
                }
            )
        },
        containerColor = colorResource(R.color.dart_background)
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(detailList) { index, item ->
                DChartDetailItem(
                    index = index,
                    label = stringResource(item.first),
                    content = item.second
                )
            }
        }
    }
}

@Composable
private fun buildDetailList(item: vn.mtk.compose.presentation.model.DCharPriceUi?): List<Pair<Int, String>> {
    return item?.let {
        listOf(
            R.string.label_date to formatTimeStamp(it.timestamp),
            R.string.label_symbol to it.symbol,
            R.string.label_open_price to "${it.openPrice}",
            R.string.label_close_price to "${it.closePrice}",
            R.string.label_highest_price to "${it.highPrice}",
            R.string.label_lowest_price to "${it.lowPrice}",
            R.string.label_volume to formatVolume(it.volume)
        )
    } ?: emptyList()
}
