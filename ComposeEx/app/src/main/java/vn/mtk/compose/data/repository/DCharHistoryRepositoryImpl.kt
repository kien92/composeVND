package vn.mtk.compose.data.repository

import vn.mtk.compose.data.local.datasource.DCharHistoryLocalDataSource
import vn.mtk.compose.data.mapper.toDomain
import vn.mtk.compose.data.mapper.toEntity
import vn.mtk.compose.data.mapper.toPriceList
import vn.mtk.compose.data.remote.service.ApiVNDService
import vn.mtk.compose.domain.model.DCharPrice
import vn.mtk.compose.domain.model.ErrorDataType
import vn.mtk.compose.domain.model.ErrorType
import vn.mtk.compose.domain.model.ResultData
import vn.mtk.compose.domain.repository.DCharHistoryRepository

class DCharHistoryRepositoryImpl(
    private val apiService: ApiVNDService, private val dataSource: DCharHistoryLocalDataSource
) : DCharHistoryRepository {
    override suspend fun getPriceHistory(
        resolution: String, symbol: String, from: Long, to: Long
    ): ResultData<List<DCharPrice>> {
        val localResult = dataSource.getDCharHistoryFromLocal(resolution, symbol, from, to)
            .sortedByDescending { it.timestamp }.distinctBy { it.timestamp }

        if (localResult.isNotEmpty()) {
            return ResultData.Success(localResult.map { it.toDomain() })
        }

        return runCatching {
            val response = apiService.getDChartHistory(resolution, symbol, from, to)
            if (response.isResponseStatusOk()) {
                val remoteData = response.toPriceList(resolution, symbol)
                    .sortedByDescending { it.timestamp }.distinctBy { it.timestamp }
                dataSource.insertPriceHistory(remoteData.map { it.toEntity() })
                ResultData.Success(remoteData)
            } else {
                ResultData.Error(ErrorType.SERVER_ERROR)
            }
        }.getOrElse {
            ResultData.Error(ErrorDataType.map(it))
        }

    }
}