package com.pedro.solutions.dialysisnotes.ui.pdf

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pedro.solutions.dialysisnotes.R
import com.pedro.solutions.dialysisnotes.data.pdf.PDF
import com.pedro.solutions.dialysisnotes.ui.Utils

@Composable
fun PDFItem(pdf: PDF, modifier: Modifier, onClick: () -> Unit) {
    val created = Utils.getDateAndTimeFromMillis(
        pdf.createdAt, Utils.DATE_FORMAT_DEFAULT, Utils.getDefaultLocale(
            LocalContext.current
        )
    )
    val startInterval = Utils.getDateAndTimeFromMillis(
        pdf.startInterval, Utils.DATE_FORMAT_DEFAULT_ONLY_DATE, Utils.getDefaultLocale(
            LocalContext.current
        )
    )
    val endInterval = Utils.getDateAndTimeFromMillis(
        pdf.endInterval, Utils.DATE_FORMAT_DEFAULT_ONLY_DATE, Utils.getDefaultLocale(
            LocalContext.current
        )
    )

    Card(shape = RoundedCornerShape(15.dp), modifier = modifier) {
        Row(modifier = Modifier.padding(5.dp)) {
            Text(text = stringResource(id = R.string.criado_as, created), fontSize = 10.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.padding(5.dp)) {
            Text(text = stringResource(id = R.string.de_as, startInterval, endInterval))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 5.dp),
            horizontalArrangement = Arrangement.End
        ) {

            Button(onClick = {
                onClick()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.pdf_icon), contentDescription = null
                )
            }

        }
    }
}