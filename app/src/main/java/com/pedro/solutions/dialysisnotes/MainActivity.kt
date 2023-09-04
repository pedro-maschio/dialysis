package com.pedro.solutions.dialysisnotes

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pedro.solutions.dialysisnotes.navigation.NavigationConstants
import com.pedro.solutions.dialysisnotes.ui.theme.DialysisNotesTheme
import com.pedro.solutions.dialysisnotes.ui.add_edit.DialysisViewModel
import com.pedro.solutions.dialysisnotes.ui.add_edit.CreateDialysis
import com.pedro.solutions.dialysisnotes.ui.dialysis_list.DialysisItem
import com.pedro.solutions.dialysisnotes.ui.dialysis_list.DialysisList

class MainActivity : ComponentActivity() {
    private val viewModel: DialysisViewModel by viewModels {
        DialysisViewModel.Factory
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DialysisNotesTheme {
                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = NavigationConstants.MAIN_SCREEN
                    ) {
                        composable(
                            NavigationConstants.CREATE_EDIT_DIALYSIS + "/{userId}",
                            arguments = listOf(navArgument("userId") {
                                type = NavType.IntType
                                defaultValue = -1
                            })
                        ) {
                            val id = it.arguments?.getInt("userId")
                            viewModel.loadDialysisItem(id)
                            CreateDialysis(
                                viewModel, navController
                            )
                        }
                        composable(NavigationConstants.MAIN_SCREEN) { MainScreen(navController) }

                    }
                }
            }
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen(navController: NavController) {
        Scaffold(topBar = {
            TopAppBar(title = { Text(text = getString(R.string.app_name)) })
        }, floatingActionButton = { FloatingButton(navController) }, content = { innerPadding ->
            DialysisList(viewModel, innerPadding, navController)
        }, modifier = Modifier.fillMaxSize())
    }

    @Composable
    fun FloatingButton(navController: NavController) {
        FloatingActionButton(onClick = { navController.navigate(NavigationConstants.CREATE_EDIT_DIALYSIS + "/-1") }) {
            Icon(Icons.Filled.Add, getString(R.string.create_new_dialysis))
        }
    }


}

