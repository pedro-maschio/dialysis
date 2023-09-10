package com.pedro.solutions.dialysisnotes.ui.pdf_generator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pedro.solutions.dialysisnotes.R
import com.pedro.solutions.dialysisnotes.ui.theme.CommonScaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditPDF(viewModel: PDFViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    var showDatePickerDialog by remember {
        mutableStateOf(false)
    }

    CommonScaffold(screenTitle = stringResource(id = R.string.add_edit_pdf)) { innerpadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerpadding)
                .verticalScroll(scrollState)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                TextField(value = uiState.patient, onValueChange = {
                    viewModel.onEvent(AddEditPDFEvent.OnPDFPatientChanged(it))
                }, label = { Text(text = stringResource(id = R.string.patient_name)) })
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                if (showDatePickerDialog) {
                    CustomDatePickerDialog(
                        onDateSelected = {
                            viewModel.onEvent(AddEditPDFEvent.OnStartIntervalChanged(it))
                        },
                        onDismiss = { showDatePickerDialog = false },
                        isDateSelectable = uiState.isDateSelectable
                    )
                }
                TextField(value = uiState.startInterval.toString(), onValueChange = {}, Modifier.onFocusChanged {
                    if (it.hasFocus) {
                        showDatePickerDialog = true
                    }
                }, label = { Text(text = stringResource(id = R.string.intervalo_inicio)) })
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                if (showDatePickerDialog) {
                    CustomDatePickerDialog(
                        onDateSelected = {
                            viewModel.onEvent(AddEditPDFEvent.OnFinalIntervalChanged(it))
                        },
                        onDismiss = { showDatePickerDialog = false },
                        isDateSelectable = uiState.isDateSelectable
                    )
                }
                TextField(value = uiState.endInterval.toString(), onValueChange = {}, Modifier.onFocusChanged {
                    if (it.hasFocus) {
                        showDatePickerDialog = true
                    }
                }, label = { Text(text = stringResource(id = R.string.intervalo_fim)) })
            }
        }
    }
}