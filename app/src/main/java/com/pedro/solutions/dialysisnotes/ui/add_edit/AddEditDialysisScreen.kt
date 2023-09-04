package com.pedro.solutions.dialysisnotes.ui.add_edit

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pedro.solutions.dialysisnotes.R
import com.pedro.solutions.dialysisnotes.navigation.NavigationConstants
import com.pedro.solutions.dialysisnotes.ui.theme.CommonScaffold

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateDialysis(viewModel: DialysisViewModel, navController: NavController) {
    val dialysisState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    CommonScaffold(screenTitle = stringResource(id = R.string.create_new_dialysis)) { innerpadding ->
        Column(
            modifier = Modifier
                .padding(innerpadding)
                .fillMaxWidth()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {
                TextField(
                    value = dialysisState.initialUF,
                    onValueChange = {
                        viewModel.onEvent(AddEditDialysisEvent.OnDialysisInitialUFChange(it))
                    },
                    label = { Text(text = stringResource(R.string.uf_inicial)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(0.9F),
                    singleLine = true,
                    isError = dialysisState.initialUFInvalid
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {
                TextField(
                    value = dialysisState.finalUF,
                    onValueChange = {
                        viewModel.onEvent(
                            AddEditDialysisEvent.OnDialysisFinalUFChange(
                                it
                            )
                        )
                    },
                    label = { Text(text = stringResource(id = R.string.uf_final)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(0.9F),
                    isError = dialysisState.finalUFInvalid,
                    singleLine = true
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {
                TextField(
                    value = dialysisState.observations,
                    onValueChange = {
                        viewModel.onEvent(
                            AddEditDialysisEvent.OnDialysisObservationChange(
                                it
                            )
                        )
                    },
                    label = { Text(text = stringResource(id = R.string.observations)) },
                    modifier = Modifier
                        .height(170.dp)
                        .fillMaxWidth(0.9F),
                    maxLines = 15,
                    isError = dialysisState.observationsInvalid
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Button(
                    onClick = {
                        viewModel.onEvent(AddEditDialysisEvent.OnDialysisSaved(dialysisState.isEditing))
                        navController.navigate(NavigationConstants.MAIN_SCREEN)
                    },
                    enabled = !dialysisState.initialUFInvalid && !dialysisState.finalUFInvalid && !dialysisState.observationsInvalid
                ) {
                    Text(text = stringResource(id = R.string.salvar))
                }
                if (dialysisState.isEditing) {
                    Button(onClick = {
                        viewModel.onEvent(AddEditDialysisEvent.OnDialysisDeleted(dialysisState.id))
                        navController.navigate(NavigationConstants.MAIN_SCREEN)
                    }, modifier = Modifier.padding(5.dp, 0.dp)) {
                        Text(text = stringResource(id = R.string.apagar))
                    }
                }
            }
        }
    }
}