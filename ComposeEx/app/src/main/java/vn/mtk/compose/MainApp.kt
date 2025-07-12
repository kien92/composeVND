package vn.mtk.compose

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import vn.mtk.compose.di.dbLocalModule
import vn.mtk.compose.di.networkModule
import vn.mtk.compose.di.repositoryModule
import vn.mtk.compose.di.useCaseModule
import vn.mtk.compose.di.viewModelModule

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApp)
            modules(
                dbLocalModule, networkModule, repositoryModule, viewModelModule, useCaseModule
            )
        }
    }
}
