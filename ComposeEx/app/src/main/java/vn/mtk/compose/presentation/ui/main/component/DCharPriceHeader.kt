package vn.mtk.compose.presentation.ui.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import vn.mtk.compose.R
import vn.mtk.compose.presentation.ui.theme.AppDimens

@Composable
fun DCharPriceHeader() {
    val headerColor = colorResource(id = R.color.color_header)
    Row(
        modifier = Modifier
            .background(headerColor)
            .fillMaxWidth()
            .height(40.dp)
            .padding(horizontal = AppDimens.ScreenSizeLarge),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.date),
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.labelSmall,
            color = Color.Black
        )
        Text(
            text = stringResource(R.string.price),
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.labelSmall,
            color = Color.Black
        )
        Text(
            text = stringResource(R.string.change),
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.labelSmall,
            color = Color.Black
        )
        Text(
            text = stringResource(R.string.volume),
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.End,
            color = Color.Black
        )
    }
}
