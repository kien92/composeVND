package vn.mtk.compose.data.local.datasource

import vn.mtk.compose.data.local.entity.DCharPriceEntity


interface DCharHistoryLocalDataSource {
    suspend fun getDCharHistoryFromLocal(
        resolution: String,
        symbol: String,
        from: Long,
        to: Long
    ): List<DCharPriceEntity>
    suspend fun insertPriceHistory(data: List<DCharPriceEntity>)
}