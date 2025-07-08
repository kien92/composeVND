package vn.mtk.compose.data.remote.service

import retrofit2.http.GET
import retrofit2.http.Query
import vn.mtk.compose.data.remote.model.DChartHistoryResponse

interface ApiVNDService {
    @GET("dchart/history")
    suspend fun getDChartHistory(
        @Query("resolution") resolution : String,
        @Query("symbol") symbol : String,
        @Query("from") from : Long,
        @Query("to") to : Long,
    ) : DChartHistoryResponse

}