package vn.mtk.compose.domain.model

import androidx.room.PrimaryKey

data class DCharPrice(
    val timestamp: Long,
    val symbol: String,
    val resolution: String,
    val openPrice: Double,
    val closePrice: Double,
    val highPrice: Double,
    val lowPrice: Double,
    val volume: Long
)

