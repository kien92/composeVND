package vn.mtk.compose.presentation.model

data class DCharPriceUi(
    val timestamp: Long,
    val symbol: String,
    val resolution: String,
    val openPrice: Double,
    val closePrice: Double,
    val highPrice: Double,
    val lowPrice: Double,
    val volume: Long
)

fun DCharPriceUi.compareTo(prev: DCharPriceUi?): PriceChange? {
    if (prev == null || prev.closePrice == 0.0) return null
    val diff = closePrice - prev.closePrice
    val percent = (diff / prev.closePrice) * 100
    return PriceChange(diff, percent)
}

