package vn.mtk.compose.domain.repository

import vn.mtk.compose.domain.model.DCharPrice
import vn.mtk.compose.domain.model.ResultData

interface DCharHistoryRepository {
    suspend fun getPriceHistory(
        resolution: String,
        symbol: String,
        from: Long,
        to: Long
    ): ResultData<List<DCharPrice>>
}