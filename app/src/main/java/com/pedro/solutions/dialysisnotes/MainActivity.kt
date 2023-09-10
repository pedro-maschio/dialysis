package com.pedro.solutions.dialysisnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
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
import com.pedro.solutions.dialysisnotes.ui.pdf_generator.AddEditPDF
import com.pedro.solutions.dialysisnotes.ui.pdf_generator.PDFList
import com.pedro.solutions.dialysisnotes.ui.pdf_generator.PDFViewModel
import com.pedro.solutions.dialysisnotes.ui.theme.DialysisNotesTheme

class MainActivity : ComponentActivity() {
    private val dialysisViewModel: DialysisViewModel by viewModels {
        DialysisViewModel.Factory
    }
    private val pdfViewModel: PDFViewModel by viewModels {
        PDFViewModel.Factory
    }
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

        val onClickFloatingButton: (destination: DialysisDestination) -> Unit = {
            when (it) {
                is DialysisDestination.AddEditDialysis -> navController.navigate(DialysisDestination.AddEditDialysis.route + "/-1")
                is DialysisDestination.AddEditPDF -> navController.navigate(DialysisDestination.AddEditPDF.route + "/-1")
                else -> navController.navigate(DialysisDestination.AddEditDialysis.route + "/-1")

            }
        }

        Scaffold(topBar = {
            TopAppBar(title = { Text(text = getString(R.string.app_name)) })
        }, floatingActionButton = {
            FloatingButton(
                navController = navController,
                onClick = onClickFloatingButton
            )
        }, bottomBar = {
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
                        },
                        label = { Text(text = stringResource(screen.resourceId)) })
                }
            }
        }, modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
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
                    dialysisViewModel.loadDialysisItem(id)
                    AddEditDialysis(dialysisViewModel, onSaveOrDeleteButtonSelected = { destination ->
                        navController.navigate(destination.route)
                    })
                }
                composable(DialysisDestination.MainScreen.route) {
                    DialysisList(dialysisViewModel, innerPadding, onItemClicked = { itemId ->
                        navController.navigate(DialysisDestination.AddEditDialysis.route + "/$itemId")
                    })
                }
                composable(DialysisDestination.PDFList.route) {
                    PDFList()
                }
                composable(DialysisDestination.AddEditPDF.route + "/{PDFId}") {
                    AddEditPDF(pdfViewModel)
                }
            }
        }
    }

    @Composable
    fun FloatingButton(navController: NavController, onClick: (DialysisDestination) -> Unit) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination?.route
        val onClickDestination = when (currentDestination) {
            DialysisDestination.MainScreen.route -> DialysisDestination.AddEditDialysis
            DialysisDestination.PDFList.route -> DialysisDestination.AddEditPDF
            else -> DialysisDestination.AddEditDialysis
        }

        val visibleDestinations =
            listOf(DialysisDestination.MainScreen.route, DialysisDestination.PDFList.route)
        /* TODO: this is not working fine, it is a known issue when a floating button
            is set visible=false and then visible=true
            https://issuetracker.google.com/issues/224005027
            https://issuetracker.google.com/issues/236018302
         */
        AnimatedVisibility(visible = visibleDestinations.contains(currentDestination)) {
            FloatingActionButton(onClick = { onClick(onClickDestination) }) {
                Icon(Icons.Filled.Add, getString(R.string.create_new_dialysis))
            }
        }
    }
}

