package com.pedro.solutions.dialysisnotes.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonScaffold(screenTitle: String, content: @Composable (PaddingValues) -> Unit) {
    return Scaffold(
        topBar = { TopAppBar(title = { Text(screenTitle) }) }, modifier = Modifier.fillMaxSize()
    ) {
        content(it)
    }
}