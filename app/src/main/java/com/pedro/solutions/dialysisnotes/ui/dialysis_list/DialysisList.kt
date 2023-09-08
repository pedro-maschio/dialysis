package com.pedro.solutions.dialysisnotes.ui.dialysis_list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.pedro.solutions.dialysisnotes.ui.Utils
import com.pedro.solutions.dialysisnotes.ui.add_edit.DialysisViewModel

@Composable
fun DialysisList(
    viewModel: DialysisViewModel,
    innerpadding: PaddingValues,
    onItemClicked: (Int?) -> Unit
) {
    val dialysisItems by viewModel.dialysisList.observeAsState(initial = listOf())
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerpadding), userScrollEnabled = true
    ) {
        var prevMonth = ""
        itemsIndexed(dialysisItems) { index, item ->
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