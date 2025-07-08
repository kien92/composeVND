package vn.mtk.compose.common

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatTimeStamp(timestampSeconds: Long): String {
    val date = Date(timestampSeconds * 1000)
    val format = SimpleDateFormat("dd/MM/yyyy\nHH:mm:ss", Locale.getDefault())
    return format.format(date)
}

fun getTodayTimeStamp(): Long {
    return System.currentTimeMillis() / 1000L
}
