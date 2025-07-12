package vn.mtk.compose.presentation.model

data class PriceChange(
    val diff: Double,
    val percent: Double
) {
    val trend: PriceTrend
        get() = when {
            diff > 0 -> PriceTrend.UP
            diff < 0 -> PriceTrend.DOWN
            else -> PriceTrend.FLAT
        }

    fun formatted(): String {
        val sign = when (trend) {
            PriceTrend.UP -> "+"
            PriceTrend.DOWN -> "-"
            else -> ""
        }
        return "$sign${"%.2f".format(kotlin.math.abs(diff))} (${sign}${"%.2f".format(kotlin.math.abs(percent))}%)"
    }
}
