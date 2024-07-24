package com.pedro.solutions.dialysisnotes.ui.dialysis_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pedro.solutions.dialysisnotes.R
import com.pedro.solutions.dialysisnotes.data.dialysis.Dialysis
import com.pedro.solutions.dialysisnotes.ui.Utils

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DialysisItem(
    dialysis: Dialysis,
    isItemSelected: Boolean = false,
    onClick: () -> (Unit),
    onLongClick: () -> (Unit),
    modifier: Modifier
) {
    val createdAt = Utils.getDateAndTimeFromMillis(
        dialysis.createdAt, Utils.DATE_FORMAT_DEFAULT, Utils.getDefaultLocale(LocalContext.current)
    )
    var showUpdated = false
    var updatedAt = ""
    if (dialysis.createdAt != dialysis.updatedAt) {
        showUpdated = true
        updatedAt = Utils.getDateAndTimeFromMillis(
            dialysis.updatedAt,
            Utils.DATE_FORMAT_DEFAULT,
            Utils.getDefaultLocale(LocalContext.current)
        )
    }

    Card(
        modifier = modifier.combinedClickable(onClick = onClick, onLongClick = onLongClick),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isItemSelected) Color(
                LocalContext.current.resources.getColor(
                    R.color.cardSelectedColor
                )
            ) else Color(
                LocalContext.current.resources.getColor(
                    R.color.cardUnselectedColor
                )
            )
        )
    ) {
        Column(modifier = Modifier.padding(horizontal = 10.dp)) {
            Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)) {
                Text(text = stringResource(id = R.string.criado_as, createdAt), fontSize = 10.sp)
                if (showUpdated) {
                    Text(
                        text = stringResource(id = R.string.atualizado, updatedAt),
                        fontSize = 10.sp,
                        modifier = Modifier.padding(5.dp, 0.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = stringResource(id = R.string.uf_inicial), fontSize = 16.sp)
                Text(text = "${dialysis.initialUf}", fontSize = 18.sp)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = stringResource(id = R.string.uf_final), fontSize = 16.sp)
                Text(text = "${dialysis.finalUf}", fontSize = 18.sp)
            }
            HorizontalDivider()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = stringResource(id = R.string.uf_total), fontSize = 16.sp)
                Text(text = "${dialysis.initialUf + dialysis.finalUf}", fontSize = 20.sp)
            }
        }
    }
}

@Preview
@Composable
fun dialysisItemPreview() {
    DialysisItem(dialysis = Dialysis(
        3243, 234098, 10, 60, "This is just an example of a dialysis note", 0
    ), onClick = { /*TODO*/ }, onLongClick = { /*TODO*/ }, modifier = Modifier
    )
}