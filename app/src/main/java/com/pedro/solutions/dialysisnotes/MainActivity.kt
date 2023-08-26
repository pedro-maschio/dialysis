package com.pedro.solutions.dialysisnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pedro.solutions.dialysisnotes.ui.theme.DialysisNotesTheme
import com.pedro.solutions.dialysisnotes.viewmodels.DialysisViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: DialysisViewModel by viewModels()


        setContent {
            DialysisNotesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val dialysisItems by viewModel.dialysisList.observeAsState(initial = listOf())
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(dialysisItems) { item ->
                            Row {
                                Text(text = item.notes, modifier = Modifier.width(50.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}