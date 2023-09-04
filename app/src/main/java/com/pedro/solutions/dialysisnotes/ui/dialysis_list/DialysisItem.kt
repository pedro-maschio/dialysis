package com.pedro.solutions.dialysisnotes.ui.dialysis_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pedro.solutions.dialysisnotes.R
import com.pedro.solutions.dialysisnotes.data.Dialysis
import com.pedro.solutions.dialysisnotes.ui.Utils

@Composable
fun DialysisItem(dialysis: Dialysis, onClick: () -> (Unit)) {
    val createdAt = Utils.getDateAndTimeFromMillis(
        dialysis.createdAt, Utils.DATE_FORMAT_DEFAULT, Utils.LOCALE_DEFAULT
    )
    var showUpdated = false
    var updatedAt = ""
    if (dialysis.createdAt != dialysis.updatedAt) {
        showUpdated = true
        updatedAt = Utils.getDateAndTimeFromMillis(
            dialysis.updatedAt, Utils.DATE_FORMAT_DEFAULT, Utils.LOCALE_DEFAULT
        )
    }

    Card(
        modifier = Modifier
            .fillMaxSize()
            .height(140.dp)
            .padding(10.dp)
            .clickable { onClick.invoke() }, shape = RoundedCornerShape(15.dp)
    ) {
        Row(modifier = Modifier.padding(5.dp)) {
            Text(text = stringResource(id = R.string.criado_as, createdAt), fontSize = 10.sp)
            if (showUpdated) {
                Text(
                    text = stringResource(id = R.string.atualizado, updatedAt),
                    fontSize = 10.sp,
                    modifier = Modifier.padding(5.dp, 0.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.padding(5.dp)) {
            Text(text = stringResource(id = R.string.uf_inicial), fontSize = 18.sp)
            Spacer(Modifier.weight(1f))
            Text(text = "${dialysis.initialUf}", fontSize = 18.sp)
        }
        Row(modifier = Modifier.padding(5.dp)) {
            Text(text = stringResource(id = R.string.uf_final), fontSize = 18.sp)
            Spacer(Modifier.weight(1f))
            Text(text = "${dialysis.finalUf}", fontSize = 18.sp)
        }
    }
}