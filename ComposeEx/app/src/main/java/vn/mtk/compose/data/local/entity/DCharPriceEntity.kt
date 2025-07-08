package vn.mtk.compose.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import vn.mtk.compose.common.Constants

@Entity(tableName = Constants.TABLE_NAME)
data class DCharPriceEntity(
    @PrimaryKey val timestamp: Long,
    val symbol: String,
    val resolution: String,
    val openPrice: Double,
    val closePrice: Double,
    val highPrice: Double,
    val lowPrice: Double,
    val volume: Long
)
