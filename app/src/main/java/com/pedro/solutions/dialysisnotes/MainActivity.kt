package com.pedro.solutions.dialysisnotes

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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
import com.pedro.solutions.dialysisnotes.ui.login.LoginScreen
import com.pedro.solutions.dialysisnotes.ui.login.LoginViewModel
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
                    MainScreen()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen() {
        val navController = rememberNavController()
        val startDestination: String =
            if (loginViewModel.isUserLoggedIn()) DialysisDestination.MainScreen.route else DialysisDestination.LoginScreen.route

        val bottomNavigationItems =
            listOf(DialysisDestination.MainScreen, DialysisDestination.PDFList)
        val bottomNavigationIcons = listOf(
            Icons.Filled.List, Icon(
                painter = painterResource(id = R.drawable.pdf_icon), contentDescription = null
            )
        )
        var selectedNavigationItem by remember {
            mutableIntStateOf(0)
        }

        val onClickFloatingButton: (destination: DialysisDestination) -> Unit = {
            when (it) {
                is DialysisDestination.AddEditDialysis -> navController.navigate(DialysisDestination.AddEditDialysis.route + "/-1")
                is DialysisDestination.AddEditPDF -> navController.navigate(DialysisDestination.AddEditPDF.route + "/-1")
                else -> navController.navigate(DialysisDestination.AddEditDialysis.route + "/-1")

            }
        }

        val drawer = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        ModalNavigationDrawer(drawerContent = {
            ModalDrawerSheet {
                Divider(modifier = Modifier.padding(0.dp, 30.dp))
                NavigationDrawerItem(label = { Text(text = "Perfil") }, icon = {
                    Icon(
                        imageVector = Icons.Filled.Person, contentDescription = "Foto de perfil"
                    )
                }, selected = false, onClick = { /*TODO*/ })
            }
        }) {


            Scaffold(topBar = {
                TopAppBar(title = { Text(text = getString(R.string.app_name)) })
            }, floatingActionButton = {
                FloatingButton(
                    navController = navController, onClick = onClickFloatingButton
                )
            }, bottomBar = {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                val route = currentDestination?.route
                if (route != null && route != DialysisDestination.LoginScreen.route) {
                    NavigationBar {
                        bottomNavigationItems.forEachIndexed { index, screen ->
                            NavigationBarItem(selected = selectedNavigationItem == index,
                                onClick = {
                                    selectedNavigationItem = index
                                    navController.navigate(screen.route)
                                },
                                // TODO: find out why this icon it is not showing
                                icon = {
                                    bottomNavigationIcons[index]
                                },

                                label = { Text(text = stringResource(screen.resourceId)) })
                        }
                    }
                }
            }, modifier = Modifier.fillMaxSize()
            ) { innerPadding ->
                NavHost(
                    navController = navController, startDestination = startDestination
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
                        AddEditDialysis(
                            dialysisViewModel,
                            onSaveOrDeleteButtonSelected = { destination ->
                                navController.navigate(destination.route)
                            })
                    }
                    composable(DialysisDestination.MainScreen.route) {
                        DialysisList(dialysisViewModel, innerPadding, onItemClicked = { itemId ->
                            navController.navigate(DialysisDestination.AddEditDialysis.route + "/$itemId")
                        })
                    }
                    composable(DialysisDestination.PDFList.route) {
                        pdfViewModel.resetState()
                        PDFList(pdfViewModel, innerPadding)
                    }
                    composable(DialysisDestination.AddEditPDF.route + "/{PDFId}") {
                        AddEditPDF(pdfViewModel) {
                            navController.navigate(it.route)
                        }
                    }

                    composable(DialysisDestination.LoginScreen.route) {
                        LoginScreen(loginViewModel = loginViewModel) {
                            navController.navigate(DialysisDestination.MainScreen.route)
                        }
                    }
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


        val floatingVisibility =
            currentDestination == null || visibleDestinations.contains(currentDestination)

        AnimatedVisibility(visible = floatingVisibility,
            enter = fadeIn() + expandIn { IntSize(width = 1, height = 1) }) {
            FloatingActionButton(onClick = { onClick(onClickDestination) }) {
                Icon(Icons.Filled.Add, getString(R.string.create_new_dialysis))
            }
        }
    }
}