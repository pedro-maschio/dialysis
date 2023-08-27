package com.pedro.solutions.dialysisnotes.views

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pedro.solutions.dialysisnotes.Utils
import com.pedro.solutions.dialysisnotes.model.Dialysis

@Composable
fun DialysisItemView(dialysis: Dialysis, onClick: () -> (Unit)) {
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
            Text(text = "Created at $createdAt", fontSize = 10.sp)
            if (showUpdated) {
                Text(text = "  Updated at $updatedAt", fontSize = 10.sp)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.padding(5.dp)) {
            Text(text = "Initial UF", fontSize = 18.sp)
            Spacer(Modifier.weight(1f))
            Text(text = "${dialysis.initialUf}", fontSize = 18.sp)
        }
        Row(modifier = Modifier.padding(5.dp)) {
            Text(text = "Final UF", fontSize = 18.sp)
            Spacer(Modifier.weight(1f))
            Text(text = "${dialysis.finalUf}", fontSize = 18.sp)
        }
    }
}