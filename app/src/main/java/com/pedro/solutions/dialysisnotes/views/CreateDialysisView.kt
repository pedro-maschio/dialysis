package com.pedro.solutions.dialysisnotes.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CreateDialysis() {
    var initialUF by remember {
        mutableStateOf("")
    }
    var finalUf by remember {
        mutableStateOf("")
    }
    var observations by remember {
        mutableStateOf("")
    }

    CommonScaffold(screenTitle = "Create new dialysis") { innerpadding ->
        Column(
            modifier = Modifier.padding(innerpadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(0.9F), horizontalArrangement = Arrangement.Center
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
                modifier = Modifier.fillMaxWidth(0.9F), horizontalArrangement = Arrangement.Center
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
                modifier = Modifier.fillMaxWidth(0.9F), horizontalArrangement = Arrangement.Center
            ) {
                TextField(
                    value = observations,
                    onValueChange = { observations = it },
                    label = { Text(text = "Observations") },
                    modifier = Modifier
                        .height(60.dp)
                        .fillMaxWidth(0.9F)
                )

            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}