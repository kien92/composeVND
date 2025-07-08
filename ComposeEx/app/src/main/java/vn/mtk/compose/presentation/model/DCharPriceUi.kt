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