package vn.mtk.compose

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import vn.mtk.compose.common.Constants
import vn.mtk.compose.data.local.datasource.DCharHistoryLocalDataSource
import vn.mtk.compose.data.local.entity.DCharPriceEntity
import vn.mtk.compose.data.mapper.toDomain
import vn.mtk.compose.data.mapper.toEntity
import vn.mtk.compose.data.mapper.toPriceList
import vn.mtk.compose.data.remote.model.DChartHistoryResponse
import vn.mtk.compose.data.remote.service.ApiVNDService
import vn.mtk.compose.data.repository.DCharHistoryRepositoryImpl
import vn.mtk.compose.domain.model.ErrorType
import vn.mtk.compose.domain.model.ResultData
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@OptIn(ExperimentalCoroutinesApi::class)
class DCharHistoryRepositoryImplTest {

    private val apiService: ApiVNDService = mockk()
    private val localDataSource: DCharHistoryLocalDataSource = mockk()
    private lateinit var repository: DCharHistoryRepositoryImpl

    private val resolution = "1D"
    private val symbol = "VND"
    private val from = 1000L
    private val to = 2000L

    @BeforeEach
    fun setUp() {
        repository = DCharHistoryRepositoryImpl(apiService, localDataSource)
    }

    @Test
    fun `when local has data returns local Success and skips api`() = runTest {
        // given: local có 2 entity (timestamp duplicate sẽ bị lọc)
        val e1 = DCharPriceEntity(5L, symbol, resolution, 1.0, 2.0, 3.0, 0.5, 10L)
        val e2 = DCharPriceEntity(5L, symbol, resolution, 1.1, 2.1, 3.1, 0.6, 11L)
        val e3 = DCharPriceEntity(3L, symbol, resolution, 4.0, 5.0, 6.0, 1.0, 20L)
        coEvery { localDataSource.getDCharHistoryFromLocal(resolution, symbol, from, to) }
            .returns(listOf(e1, e2, e3))

        // when
        val result = repository.getPriceHistory(resolution, symbol, from, to)

        // then: chỉ lấy e1 (ts=5) và e3, theo thứ tự timestamp desc
        val expectedDomain = listOf(e1, e3).map { it.toDomain() }
        assertEquals(ResultData.Success(expectedDomain), result)

        // apiService không bao giờ được gọi
        coVerify(exactly = 0) { apiService.getDChartHistory(any(), any(), any(), any()) }
        // không insert gì vào local
        coVerify(exactly = 0) { localDataSource.insertPriceHistory(any()) }
    }

    @Test
    fun `when local empty and api returns ok then returns Success and inserts`() = runTest {
        // given: cache rỗng
        coEvery { localDataSource.getDCharHistoryFromLocal(resolution, symbol, from, to) }
            .returns(emptyList())

        // chuẩn bị response ok với 2 điểm dữ liệu
        val tsList = listOf(10L, 5L)
        val resp = DChartHistoryResponse(
            closePrice = listOf(20.0, 15.0),
            highPrice = listOf(25.0, 18.0),
            lowPrice = listOf(10.0, 12.0),
            openPrice = listOf(15.0, 14.0),
            status = Constants.STATUS_SUCCESS,
            timestamp = tsList,
            volume = listOf(100L, 200L)
        )
        coEvery { apiService.getDChartHistory(resolution, symbol, from, to) } returns resp

        // when
        val result = repository.getPriceHistory(resolution, symbol, from, to)

        // then
        // 1. Should insert mapped & sorted entities into local
        val expectedEntities = resp.toPriceList(resolution, symbol)
            .map { it.toEntity() }
        coVerify(exactly = 1) { localDataSource.insertPriceHistory(expectedEntities) }

        // 2. Should return Success(mapped domain, sorted by timestamp desc)
        val expectedDomain = resp.toPriceList(resolution, symbol)
        assertEquals(ResultData.Success(expectedDomain), result)
    }

    @Test
    fun `when local empty and api returns error status then returns SERVER_ERROR`() = runTest {
        // given
        coEvery { localDataSource.getDCharHistoryFromLocal(resolution, symbol, from, to) }
            .returns(emptyList())
        val errorResp = DChartHistoryResponse(
            closePrice = emptyList(), highPrice = emptyList(),
            lowPrice = emptyList(), openPrice = emptyList(),
            status = "error", timestamp = emptyList(), volume = emptyList()
        )
        coEvery { apiService.getDChartHistory(resolution, symbol, from, to) } returns errorResp

        // when
        val result = repository.getPriceHistory(resolution, symbol, from, to)

        // then
        assertEquals(ResultData.Error(ErrorType.SERVER_ERROR), result)
        coVerify(exactly = 0) { localDataSource.insertPriceHistory(any()) }
    }

    @Test
    fun `when local empty and api throws exception then returns mapped ErrorType`() = runTest {
        // given
        coEvery { localDataSource.getDCharHistoryFromLocal(resolution, symbol, from, to) }
            .returns(emptyList())
        coEvery { apiService.getDChartHistory(resolution, symbol, from, to) }
            .throws(UnknownHostException())

        // when
        val resultNoInternet = repository.getPriceHistory(resolution, symbol, from, to)

        // then
        assertEquals(ResultData.Error(ErrorType.NO_INTERNET), resultNoInternet)

        // and when timeout
        coEvery { apiService.getDChartHistory(resolution, symbol, from, to) }
            .throws(SocketTimeoutException())

        val resultTimeout = repository.getPriceHistory(resolution, symbol, from, to)
        assertEquals(ResultData.Error(ErrorType.TIMEOUT), resultTimeout)
    }
}