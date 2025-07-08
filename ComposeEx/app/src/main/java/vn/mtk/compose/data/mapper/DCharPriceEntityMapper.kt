package vn.mtk.compose.data.mapper

import vn.mtk.compose.data.local.entity.DCharPriceEntity
import vn.mtk.compose.domain.model.DCharPrice

fun DCharPriceEntity.toDomain(): DCharPrice {
    return DCharPrice(
        timestamp = this.timestamp,
        symbol = this.symbol,
        resolution = this.resolution,
        openPrice = this.openPrice,
        closePrice = this.closePrice,
        highPrice = this.highPrice,
        lowPrice = this.lowPrice,
        volume = this.volume
    )
}

fun DCharPrice.toEntity(): DCharPriceEntity {
    return DCharPriceEntity(
        timestamp = this.timestamp,
        symbol = this.symbol,
        resolution = this.resolution,
        openPrice = this.openPrice,
        closePrice = this.closePrice,
        highPrice = this.highPrice,
        lowPrice = this.lowPrice,
        volume = this.volume
    )
}