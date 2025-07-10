package vn.mtk.compose.presentation.mapper

import vn.mtk.compose.domain.model.DCharPrice
import vn.mtk.compose.presentation.model.DCharPriceUi

fun DCharPrice.toUiModel(): DCharPriceUi {
    return DCharPriceUi(
        timestamp = timestamp,
        symbol = symbol,
        resolution = resolution,
        openPrice = openPrice,
        closePrice = closePrice,
        highPrice = highPrice,
        lowPrice = lowPrice,
        volume = volume
    )
}