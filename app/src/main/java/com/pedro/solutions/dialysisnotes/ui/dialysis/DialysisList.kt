package com.pedro.solutions.dialysisnotes.ui.dialysis

import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pedro.solutions.dialysisnotes.R
import com.pedro.solutions.dialysisnotes.data.dialysis.Dialysis
import com.pedro.solutions.dialysisnotes.ui.components.EmptyDialysisListIndicator

@Composable
fun DialysisList(
    dialysisList: List<Dialysis>,
    innerpadding: PaddingValues,
    onItemClicked: (Int?) -> Unit,
    onSelectedItemsChanged: (List<Int?>) -> Unit
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

    val selectedItems = remember {
        mutableStateListOf<Int?>()
    }
    var isInSelectionMode by remember {
        mutableStateOf(false)
    }

    if (selectedItems.isEmpty()) isInSelectionMode = false

    val resetSelectionMode = {
        isInSelectionMode = false
        selectedItems.clear()
        onSelectedItemsChanged(selectedItems)
    }
    BackHandler(enabled=isInSelectionMode) {
        resetSelectionMode()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerpadding), userScrollEnabled = true
    ) {
        var prevMonth = ""
        itemsIndexed(dialysisList) { index, item ->

            val isItemSelected = selectedItems.contains(item.id)
            DialysisItem(
                dialysis = item,
                isItemSelected = isItemSelected,
                onClick = {
                    if (isInSelectionMode) {
                        if (selectedItems.contains(item.id))
                            selectedItems.remove(item.id)
                        else
                            selectedItems.add(item.id)

                        onSelectedItemsChanged(selectedItems.toList())
                    } else {
                        onItemClicked(item.id)
                    }

                },
                onLongClick = {
                    isInSelectionMode = true
                    if (selectedItems.contains(item.id))
                        selectedItems.remove(item.id)
                    else
                        selectedItems.add(item.id)
                    onSelectedItemsChanged(selectedItems.toList())
                },
                Modifier
                    .fillMaxSize()
                    .height(160.dp)
                    .padding(10.dp)
            )
        }
    }
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
        onSelectedItemsChanged = {},
        onItemClicked = {
            // Handle item click if needed
        }
    )
}