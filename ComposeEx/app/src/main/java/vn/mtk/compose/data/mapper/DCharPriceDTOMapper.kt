package vn.mtk.compose.data.mapper

import vn.mtk.compose.data.remote.model.DChartHistoryResponse
import vn.mtk.compose.domain.model.DCharPrice

fun DChartHistoryResponse.toPriceList(resolution: String, symbol: String): List<DCharPrice> {
    val size = listOf(
        timestamp.size, openPrice.size, closePrice.size, highPrice.size, lowPrice.size, volume.size
    ).minOrNull() ?: 0

    return (0 until size).map { i ->
        DCharPrice(
            timestamp = timestamp[i],
            symbol = symbol,
            resolution = resolution,
            openPrice = openPrice[i],
            closePrice = closePrice[i],
            highPrice = highPrice[i],
            lowPrice = lowPrice[i],
            volume = volume[i]
        )
    }
}
