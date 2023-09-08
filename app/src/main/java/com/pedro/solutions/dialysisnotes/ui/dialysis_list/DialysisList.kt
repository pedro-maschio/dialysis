package com.pedro.solutions.dialysisnotes.ui.dialysis_list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.pedro.solutions.dialysisnotes.navigation.AddEditDialysis
import com.pedro.solutions.dialysisnotes.navigation.DialysisDestination
import com.pedro.solutions.dialysisnotes.ui.add_edit.DialysisViewModel

@Composable
fun DialysisList(viewModel: DialysisViewModel, innerpadding: PaddingValues, onItemClicked: (AddEditDialysis, Int?) -> Unit) {
    val dialysisItems by viewModel.dialysisList.observeAsState(initial = listOf())
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerpadding), userScrollEnabled = true
    ) {
        items(dialysisItems) { item ->
            DialysisItem(dialysis = item) {
                onItemClicked(AddEditDialysis, item.id)
//                navController.navigate(
//                    AddEditDialysis.route + "/${item.id}"
//                )
            }
        }
    }
}