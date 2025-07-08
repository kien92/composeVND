package vn.mtk.compose.data.local.datasource

import vn.mtk.compose.data.local.dao.DCharPriceDao
import vn.mtk.compose.data.local.entity.DCharPriceEntity


class DCharHistoryLocalDataSourceImpl(private val dao: DCharPriceDao) :
    DCharHistoryLocalDataSource {
    override suspend fun getDCharHistoryFromLocal(
        resolution: String,
        symbol: String,
        from: Long,
        to: Long
    ): List<DCharPriceEntity> = dao.getDCharHistoryFromLocal(resolution, symbol, from, to)

    override suspend fun insertPriceHistory(data: List<DCharPriceEntity>) = dao.insertAll(data)
}
