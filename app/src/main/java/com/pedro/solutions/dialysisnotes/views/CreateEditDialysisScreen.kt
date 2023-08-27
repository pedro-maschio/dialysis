package com.pedro.solutions.dialysisnotes.views

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.pedro.solutions.dialysisnotes.model.Dialysis
import com.pedro.solutions.dialysisnotes.navigation.NavigationConstants
import com.pedro.solutions.dialysisnotes.viewmodels.DialysisViewModel
import kotlinx.coroutines.flow.collect

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateDialysis(viewModel: DialysisViewModel, navController: NavController) {
    var initialUF by remember {
        mutableStateOf("")
    }
    var finalUf by remember {
        mutableStateOf("")
    }
    var observations by remember {
        mutableStateOf("")
    }
    val dialysis = viewModel.dialysisItem?.collectAsState(null)
    Log.d("PEDRO123", dialysis.toString())
    dialysis?.value?.let {
        initialUF = it.initialUf.toString()
        finalUf = it.finalUf.toString()
        observations = it.notes
    }


    CommonScaffold(screenTitle = "Create new dialysis") { innerpadding ->
        Column(
            modifier = Modifier
                .padding(innerpadding)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {
                TextField(
                    value = initialUF,
                    onValueChange = { initialUF = it },
                    label = { Text(text = "Initial UF") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(0.9F)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {
                TextField(
                    value = finalUf,
                    onValueChange = { finalUf = it },
                    label = { Text(text = "Final UF") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(0.9F)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {
                TextField(
                    value = observations,
                    onValueChange = { observations = it },
                    label = { Text(text = "Observations") },
                    modifier = Modifier
                        .height(170.dp)
                        .fillMaxWidth(0.9F),
                    maxLines = 15,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                val newEntry = Dialysis(
                    System.currentTimeMillis(),
                    System.currentTimeMillis(),
                    if (initialUF.isEmpty()) 0 else initialUF.toInt(),
                    if (finalUf.isEmpty()) 0 else finalUf.toInt(),
                    observations,
                    null
                )
                viewModel.addDialysis(newEntry)
                navController.navigate(NavigationConstants.MAIN_SCREEN)
            }) {
                Text(text = "Save")
            }
        }
    }
}