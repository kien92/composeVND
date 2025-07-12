package vn.mtk.compose.presentation.mapper

import vn.mtk.compose.domain.model.DCharPrice
import vn.mtk.compose.presentation.model.DCharPriceUi

/**
 * Class mapper model từ Domain layer -> cho tầng Presentation layer để view trên màn hình
 * Class này tách biệt model dữ liệu và model cho riêng view
 */
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