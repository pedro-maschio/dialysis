package com.pedro.solutions.dialysisnotes.ui.pdf_generator

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pedro.solutions.dialysisnotes.R
import com.pedro.solutions.dialysisnotes.navigation.DialysisDestination
import com.pedro.solutions.dialysisnotes.ui.Utils
import com.pedro.solutions.dialysisnotes.ui.theme.CommonScaffold

@Composable
fun AddEditPDF(viewModel: PDFViewModel, onGeneratePDFButtonClicked: (DialysisDestination) -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    var showFirstDatePickerDialog by remember {
        mutableStateOf(false)
    }
    var showSecondDatePickerDialog by remember {
        mutableStateOf(false)
    }

    val result = remember { mutableStateOf<Uri?>(null) }

    val launcherActivity =
        rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument("application/pdf")) {
            result.value = it
            viewModel.generatePDF(result.value, uiState.patient, uiState.startInterval, uiState.endInterval)
            onGeneratePDFButtonClicked(DialysisDestination.PDFList)
        }
    CommonScaffold(screenTitle = stringResource(id = R.string.add_edit_pdf)) { innerpadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerpadding)
                .verticalScroll(scrollState)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                TextField(value = uiState.patient,
                    onValueChange = {
                        viewModel.onEvent(AddEditPDFEvent.OnPDFPatientChanged(it))
                    },
                    label = { Text(text = stringResource(id = R.string.patient_name)) },
                    modifier = Modifier.fillMaxWidth(0.9F)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                if (showFirstDatePickerDialog) {
                    CustomDatePickerDialog(onDateSelected = {
                        viewModel.onEvent(AddEditPDFEvent.OnStartIntervalChanged(it))
                    },
                        onDismiss = { showFirstDatePickerDialog = false },
                        isDateSelectable = uiState.isDateSelectableStartInterval
                    )
                }
                TextField(value = Utils.getDateAndTimeFromMillis(
                    uiState.startInterval,
                    Utils.DATE_FORMAT_DEFAULT_ONLY_DATE,
                    Utils.getDefaultLocale(
                        LocalContext.current
                    )
                ),
                    onValueChange = {},
                    Modifier
                        .onFocusChanged {
                            if (it.hasFocus) {
                                showFirstDatePickerDialog = true
                            }
                        }
                        .fillMaxWidth(0.9F),
                    label = { Text(text = stringResource(id = R.string.intervalo_inicio)) })
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                if (showSecondDatePickerDialog) {
                    CustomDatePickerDialog(onDateSelected = {
                        viewModel.onEvent(AddEditPDFEvent.OnFinalIntervalChanged(it))
                    },
                        onDismiss = { showSecondDatePickerDialog = false },
                        isDateSelectable = uiState.isDateSelectableEndInterval
                    )
                }
                TextField(value = Utils.getDateAndTimeFromMillis(
                    uiState.endInterval,
                    Utils.DATE_FORMAT_DEFAULT_ONLY_DATE,
                    Utils.getDefaultLocale(
                        LocalContext.current
                    )
                ),
                    onValueChange = {},
                    Modifier
                        .onFocusChanged {
                            if (it.hasFocus) {
                                showSecondDatePickerDialog = true
                            }
                        }
                        .fillMaxWidth(0.9F),
                    label = { Text(text = stringResource(id = R.string.intervalo_fim)) })
            }
            Spacer(modifier = Modifier.height(30.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                val context = LocalContext.current
                Button(onClick = {
                    launcherActivity.launch(
                        Utils.generatePDFFileName(context, uiState.startInterval, uiState.endInterval)
                    )
                }) {
                    Text(text = stringResource(id = R.string.gerar_pdf))
                }
            }
        }
    }
}