package vn.mtk.compose.domain.usecase

import vn.mtk.compose.domain.model.DCharPrice
import vn.mtk.compose.domain.model.ResultData
import vn.mtk.compose.domain.repository.DCharHistoryRepository

class DChartHistoryUseCase(private val repository: DCharHistoryRepository) {
    suspend operator fun invoke(
        resolution: String, symbol: String, from: Long, to: Long
    ): ResultData<List<DCharPrice>> {
        return repository.getPriceHistory(resolution, symbol, from, to)
    }

}