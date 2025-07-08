package vn.mtk.compose.di

import org.koin.dsl.module
import vn.mtk.compose.data.repository.DCharHistoryRepositoryImpl
import vn.mtk.compose.domain.repository.DCharHistoryRepository

val repositoryModule = module {
    single<DCharHistoryRepository> { DCharHistoryRepositoryImpl(get(), get()) }
}
