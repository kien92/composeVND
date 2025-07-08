package vn.mtk.compose.data.remote.model

import com.google.gson.annotations.SerializedName
import vn.mtk.compose.common.Constants

data class DChartHistoryResponse(
    @SerializedName("c") val closePrice: List<Double>,
    @SerializedName("h") val highPrice: List<Double>,
    @SerializedName("l") val lowPrice: List<Double>,
    @SerializedName("o") val openPrice: List<Double>,
    @SerializedName("s") val status: String,
    @SerializedName("t") val timestamp: List<Long>,
    @SerializedName("v") val volume: List<Long>
) {
    fun isResponseStatusOk(): Boolean {
        return status.equals(Constants.STATUS_SUCCESS, ignoreCase = true)
    }
}
