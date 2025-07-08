package vn.mtk.compose.di

import org.koin.dsl.module
import vn.mtk.compose.domain.usecase.DChartHistoryUseCase

val useCaseModule = module {
    single { DChartHistoryUseCase(get()) }
}
