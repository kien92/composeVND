package vn.mtk.compose.domain.model

sealed class ResultData<out T> {
    data class Success<out T>(val data: T) : ResultData<T>()
    data class Error(val errorType: ErrorType) : ResultData<Nothing>()
}