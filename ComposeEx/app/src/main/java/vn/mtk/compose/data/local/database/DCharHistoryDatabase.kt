package vn.mtk.compose.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import vn.mtk.compose.common.Constants
import vn.mtk.compose.data.local.dao.DCharPriceDao
import vn.mtk.compose.data.local.entity.DCharPriceEntity

@Database(
    entities = [DCharPriceEntity::class],
    version = Constants.DB_VERSION,
    exportSchema = false
)

abstract class DCharHistoryDatabase : RoomDatabase() {
    abstract fun getDCharPriceDao(): DCharPriceDao

    companion object {
        @Volatile
        private var INSTANCE: DCharHistoryDatabase? = null
        fun getInstance(context: Context): DCharHistoryDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    DCharHistoryDatabase::class.java,
                    Constants.DB_NAME
                ).build().also { INSTANCE = it }
            }
        }
    }

}