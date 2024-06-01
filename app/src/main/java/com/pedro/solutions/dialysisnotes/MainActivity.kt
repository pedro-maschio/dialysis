package com.pedro.solutions.dialysisnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.pedro.solutions.dialysisnotes.navigation.MainScreen
import com.pedro.solutions.dialysisnotes.ui.add_edit.DialysisViewModel
import com.pedro.solutions.dialysisnotes.ui.login.LoginViewModel
import com.pedro.solutions.dialysisnotes.ui.pdf_generator.PDFViewModel
import com.pedro.solutions.dialysisnotes.ui.theme.DialysisNotesTheme


class MainActivity : ComponentActivity() {
    private val dialysisViewModel: DialysisViewModel by viewModels {
        DialysisViewModel.Factory
    }
    private val pdfViewModel: PDFViewModel by viewModels {
        PDFViewModel.Factory
    }

    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModel.Factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DialysisNotesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(dialysisViewModel, pdfViewModel, loginViewModel)
                }
            }
        }
    }
}
