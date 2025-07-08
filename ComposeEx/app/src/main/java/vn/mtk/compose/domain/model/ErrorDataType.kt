package vn.mtk.compose.domain.model

import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ErrorDataType {
    fun map(throwable: Throwable): ErrorType = when (throwable) {
        is UnknownHostException -> ErrorType.NO_INTERNET
        is SocketTimeoutException -> ErrorType.TIMEOUT
        else -> ErrorType.UNKNOWN
    }
}