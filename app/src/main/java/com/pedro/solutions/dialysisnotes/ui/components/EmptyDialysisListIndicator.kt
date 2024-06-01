package com.pedro.solutions.dialysisnotes.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pedro.solutions.dialysisnotes.R

@Composable
fun EmptyDialysisListIndicator(
    icon: ImageVector,
    @StringRes message: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(300.dp)
            .height(300.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(message),
            fontSize = 26.sp,
            fontWeight = FontWeight.W500,
            lineHeight = 31.sp, textAlign = TextAlign.Center
        )
        Image(
            imageVector = icon,
            contentDescription = null,
            alignment = Alignment.Center,
            modifier = Modifier
                .height(100.dp)
                .width(100.dp)
        )

    }

}

@Preview(showBackground = true)
@Composable
fun EmptyDialysisListIndicatorPreview() {
    EmptyDialysisListIndicator(Icons.Filled.Add, R.string.empty_dialysis_list_message)
}