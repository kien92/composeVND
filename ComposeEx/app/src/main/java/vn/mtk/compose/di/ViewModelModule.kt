package vn.mtk.compose.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import vn.mtk.compose.presentation.viewmodel.MainViewModel

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}
