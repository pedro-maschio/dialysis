package com.pedro.solutions.dialysisnotes.ui.pdf_generator

import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerDialog(
    onDateSelected: (Long) -> Unit, onDismiss: () -> Unit, isDateSelectable: (Long) -> Boolean
) {
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis ?: System.currentTimeMillis()

    DatePickerDialog(onDismissRequest = { onDismiss() }, confirmButton = {
        Button(onClick = {
            onDismiss()
            onDateSelected(selectedDate)
        }) {
            Text(text = "Ok")
        }
    }, dismissButton = {
        Button(onClick = {
            onDismiss()
        }) {
            Text(text = "Cancelar")
        }
    }) {
        DatePicker(state = datePickerState, dateValidator = {
            isDateSelectable(it)
        })
    }
}