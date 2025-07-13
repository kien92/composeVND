package vn.mtk.compose

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import vn.mtk.compose.domain.model.DCharPrice
import vn.mtk.compose.domain.model.ErrorType
import vn.mtk.compose.domain.model.ResultData
import vn.mtk.compose.domain.repository.DCharHistoryRepository
import vn.mtk.compose.domain.usecase.DChartHistoryUseCase


@OptIn(ExperimentalCoroutinesApi::class)
class DChartHistoryUseCaseTest {
    // Mock repository
    private val repository: DCharHistoryRepository = mockk()

    // System under test
    private lateinit var useCase: DChartHistoryUseCase

    @BeforeEach
    fun setUp() {
        useCase = DChartHistoryUseCase(repository)
    }

    @Test
    fun `when repository returns Success then UseCase returns same Success`() = runTest {
        // given
        val samplePrice = DCharPrice(
            timestamp = 1L,
            symbol = "VND",
            resolution = "1D",
            openPrice = 10.0,
            closePrice = 12.0,
            highPrice = 15.0,
            lowPrice = 8.0,
            volume = 1000L
        )
        val expected = ResultData.Success(listOf(samplePrice))
        coEvery { repository.getPriceHistory("1D", "VND", 0L, 100L) } returns expected

        // when
        val actual = useCase.invoke("1D", "VND", 0L, 100L)

        // then
        assertEquals(expected, actual)
        coVerify(exactly = 1) { repository.getPriceHistory("1D", "VND", 0L, 100L) }
    }

    @Test
    fun `when repository returns Error then UseCase returns same Error`() = runTest {
        // given
        val expectedError = ResultData.Error(ErrorType.SERVER_ERROR)
        coEvery { repository.getPriceHistory(any(), any(), any(), any()) } returns expectedError

        // when
        val actual = useCase("5m", "VND", 10L, 20L)

        // then
        assertEquals(expectedError, actual)
        coVerify(exactly = 1) { repository.getPriceHistory("5m", "VND", 10L, 20L) }
    }
}