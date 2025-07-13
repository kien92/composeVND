package vn.mtk.compose.presentation.model

data class DCharPriceChange(
    val diff: Double,
    val percent: Double
) {
    val trend: DCharPriceTrend
        get() = when {
            diff > 0 -> DCharPriceTrend.UP
            diff < 0 -> DCharPriceTrend.DOWN
            else -> DCharPriceTrend.FLAT
        }

    fun formatted(): String {
        val sign = when (trend) {
            DCharPriceTrend.UP -> "+"
            DCharPriceTrend.DOWN -> "-"
            else -> ""
        }
        return "$sign${"%.2f".format(kotlin.math.abs(diff))} (${sign}${"%.2f".format(kotlin.math.abs(percent))}%)"
    }
}
