package vn.mtk.compose.presentation.ui.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import vn.mtk.compose.common.formatTimeStamp
import vn.mtk.compose.common.formatVolume
import vn.mtk.compose.common.toColor
import vn.mtk.compose.presentation.model.DCharPriceUi
import vn.mtk.compose.presentation.model.compareTo
import vn.mtk.compose.presentation.ui.theme.AppDimens

@Composable
fun DCharPriceItemList (
    index: Int,
    currentRecord: DCharPriceUi,
    previousRecord: DCharPriceUi?,
    clickable: () -> Unit
) {
    val backgroundColor = if (index % 2 == 0) Color.Transparent else Color.DarkGray
    val priceChange = currentRecord.compareTo(previousRecord)
    val priceColor = priceChange?.trend?.toColor() ?: Color.White
    val changeText = priceChange?.formatted() ?: "--"

    Row(
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxWidth()
            .height(60.dp)
            .clickable(onClick = {
                clickable.invoke()
            })
            .padding(horizontal = AppDimens.ScreenSizeLarge),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            formatTimeStamp(currentRecord.timestamp),
            modifier = Modifier.weight(1f),
            fontSize = 12.sp,
            color = Color.White
        )
        Text(
            "${currentRecord.closePrice}",
            modifier = Modifier.weight(1f),
            fontSize = 12.sp,
            color = priceColor
        )
        Text(
            changeText,
            modifier = Modifier.weight(1f),
            fontSize = 12.sp,
            color = priceColor
        )
        Text(
            formatVolume(currentRecord.volume),
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End,
            fontSize = 12.sp,
            color = Color.White
        )
    }
}
