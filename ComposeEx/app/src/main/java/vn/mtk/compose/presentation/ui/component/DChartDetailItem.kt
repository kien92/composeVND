package vn.mtk.compose.presentation.ui.component

import androidx.compose.foundation.background
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
import vn.mtk.compose.presentation.ui.theme.AppDimens

@Composable
fun DChartDetailItem(index: Int, label: String, content: String, contentColor: Color = Color.White) {
    val backgroundColor =  if (index % 2 == 0) Color(0xffd5d8dc)  else Color(0xFF808b96)
    Row(
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = AppDimens.ScreenSizeLarge),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            label,
            modifier = Modifier.weight(1f),
            fontSize = 12.sp,
            color = Color.White
        )
        Text(
            content,
            modifier = Modifier.weight(2f),
            textAlign = TextAlign.End,
            fontSize = 12.sp,
            color = contentColor
        )
    }
}
