package com.pedro.solutions.dialysisnotes

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pedro.solutions.dialysisnotes.navigation.DialysisDestination
import com.pedro.solutions.dialysisnotes.ui.add_edit.AddEditDialysis
import com.pedro.solutions.dialysisnotes.ui.add_edit.DialysisViewModel
import com.pedro.solutions.dialysisnotes.ui.dialysis_list.DialysisList
import com.pedro.solutions.dialysisnotes.ui.pdf_generator.PDFList
import com.pedro.solutions.dialysisnotes.ui.theme.DialysisNotesTheme

class MainActivity : ComponentActivity() {
    private val viewModel: DialysisViewModel by viewModels {
        DialysisViewModel.Factory
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DialysisNotesTheme {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen() {
        val navController = rememberNavController()

        val bottomNavigationItems =
            listOf(DialysisDestination.MainScreen, DialysisDestination.PDFList)
        val bottomNavigationIcons = listOf(
            Icons.Filled.List, Icon(
                painter = painterResource(id = R.drawable.pdf_icon), contentDescription = null
            )
        )

        Scaffold(topBar = {
            TopAppBar(title = { Text(text = getString(R.string.app_name)) })
        }, floatingActionButton = { FloatingButton(navController) }, bottomBar = {
            BottomNavigation(backgroundColor = MaterialTheme.colorScheme.primary) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                bottomNavigationItems.forEachIndexed { index, screen ->
                    BottomNavigationItem(selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route)
                        },
                        // TODO: find out why this icon it is not showing
                        icon = {
                            bottomNavigationIcons[index]
                        }, label = { Text(text = stringResource(screen.resourceId))})
                }
            }
        }, modifier = Modifier.fillMaxSize()) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = DialysisDestination.MainScreen.route
            ) {
                composable(
                    DialysisDestination.AddEditDialysis.route + "/{userId}",
                    arguments = listOf(navArgument("userId") {
                        type = NavType.IntType
                        defaultValue = -1
                    })
                ) {
                    val id = it.arguments?.getInt("userId")
                    viewModel.loadDialysisItem(id)
                    AddEditDialysis(viewModel, onSaveOrDeleteButtonSelected = { destination ->
                        navController.navigate(destination.route)
                    })
                }
                composable(DialysisDestination.MainScreen.route) {
                    DialysisList(viewModel, innerPadding, onItemClicked = { itemId ->
                        navController.navigate(DialysisDestination.AddEditDialysis.route + "/$itemId")
                    })
                }
                composable(DialysisDestination.PDFList.route) {
                    PDFList()
                }
            }
        }
    }

    @Composable
    fun FloatingButton(navController: NavController) {
        FloatingActionButton(onClick = { navController.navigate(DialysisDestination.AddEditDialysis.route + "/-1") }) {
            Icon(Icons.Filled.Add, getString(R.string.create_new_dialysis))
        }
    }
}

