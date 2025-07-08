package vn.mtk.compose.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import vn.mtk.compose.common.Constants
import vn.mtk.compose.data.local.dao.DCharPriceDao
import vn.mtk.compose.data.local.database.DCharHistoryDatabase
import vn.mtk.compose.data.local.datasource.DCharHistoryLocalDataSource
import vn.mtk.compose.data.local.datasource.DCharHistoryLocalDataSourceImpl

val dbLocalModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            DCharHistoryDatabase::class.java,
            Constants.DB_NAME
        ).build()
    }

    single<DCharPriceDao> {
        get<DCharHistoryDatabase>().getDCharPriceDao()
    }

    single<DCharHistoryLocalDataSource> {
        DCharHistoryLocalDataSourceImpl(get())
    }
}
