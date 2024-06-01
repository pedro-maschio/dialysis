package com.pedro.solutions.dialysisnotes.ui.dialysis_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pedro.solutions.dialysisnotes.R
import com.pedro.solutions.dialysisnotes.data.dialysis.Dialysis
import com.pedro.solutions.dialysisnotes.ui.Utils
import com.pedro.solutions.dialysisnotes.ui.components.EmptyDialysisListIndicator

@Composable
fun DialysisList(
    dialysisList: List<Dialysis>,
    innerpadding: PaddingValues,
    onItemClicked: (Int?) -> Unit
) {
    if (dialysisList.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmptyDialysisListIndicator(
                Icons.Filled.Add,
                R.string.empty_dialysis_list_message,
                Modifier.padding(innerpadding)
            )
        }
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerpadding), userScrollEnabled = true
    ) {
        var prevMonth = ""
        itemsIndexed(dialysisList) { index, item ->
            val currentMonth = Utils.getMonthNameFromInteger(
                item.createdAt, Utils.getDefaultLocale(
                    LocalContext.current
                )
            )
            if (index == 0 || (currentMonth != prevMonth)) {
                Month(
                    Utils.getMonthNameFromInteger(
                        item.createdAt, Utils.getDefaultLocale(LocalContext.current)
                    ), Modifier.padding(10.dp, 5.dp)
                )
                prevMonth = currentMonth
            }
            DialysisItem(
                dialysis = item,
                Modifier
                    .fillMaxSize()
                    .height(160.dp)
                    .padding(10.dp)
            ) {
                onItemClicked(item.id)
            }
        }
    }
}

@Composable
fun Month(monthName: String, modifier: Modifier) {
    Text(text = monthName, modifier = modifier)
}


@Preview
@Composable
fun DialysisListPreview() {
    DialysisList(
        dialysisList = listOf(
//            Dialysis(createdAt = System.currentTimeMillis(), System.currentTimeMillis(), 40, 90, "Just a preview", null),
//            Dialysis(createdAt = System.currentTimeMillis(), System.currentTimeMillis(), 40, 90, "Just a preview", null),
//            Dialysis(createdAt = System.currentTimeMillis(), System.currentTimeMillis(), 40, 90, "Just a preview", null),
//            Dialysis(createdAt = System.currentTimeMillis(), System.currentTimeMillis(), 40, 90, "Just a preview", null),
//            Dialysis(createdAt = System.currentTimeMillis(), System.currentTimeMillis(), 40, 90, "Just a preview", null),
        ),
        innerpadding = PaddingValues(20.dp),
        onItemClicked = {
            // Handle item click if needed
        }
    )
}