package vn.mtk.compose.common

import android.annotation.SuppressLint
import androidx.compose.ui.graphics.Color
import vn.mtk.compose.presentation.model.DCharPriceTrend
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

@SuppressLint("DefaultLocale")
fun formatVolume(volume: Long): String {
    return when {
        volume >= 1_000_000_000 -> String.format("%.1f B", volume / 1_000_000_000.0)
        volume >= 1_000_000 -> String.format("%.1f M", volume / 1_000_000.0)
        volume >= 1_000 -> String.format("%.0f K", volume / 1_000.0)
        else -> volume.toString()
    }
}

fun DCharPriceTrend.toColor(): Color = when (this) {
    DCharPriceTrend.UP -> Color.Green
    DCharPriceTrend.DOWN -> Color.Red
    DCharPriceTrend.FLAT -> Color.Yellow
}


