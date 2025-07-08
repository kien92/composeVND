package vn.mtk.compose.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import vn.mtk.compose.data.local.entity.DCharPriceEntity

@Dao
interface DCharPriceDao{
    @Query("""
    SELECT * FROM dChart_price_data_tbl 
    WHERE symbol = :symbol 
      AND resolution = :resolution 
      AND timestamp BETWEEN :from AND :to
    ORDER BY timestamp DESC
""")
    suspend fun getDCharHistoryFromLocal(
        resolution: String,
        symbol: String,
        from: Long,
        to: Long
    ): List<DCharPriceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: List<DCharPriceEntity>)

}
