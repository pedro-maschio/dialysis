package com.pedro.solutions.dialysisnotes.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pedro.solutions.dialysisnotes.R
import com.pedro.solutions.dialysisnotes.ui.add.AddEditDialysis
import com.pedro.solutions.dialysisnotes.ui.add.AddEditDialysisEvent
import com.pedro.solutions.dialysisnotes.ui.dialysis.DialysisViewModel
import com.pedro.solutions.dialysisnotes.ui.dialysis.DialysisList
import com.pedro.solutions.dialysisnotes.ui.login.LoginScreen
import com.pedro.solutions.dialysisnotes.ui.login.LoginViewModel
import com.pedro.solutions.dialysisnotes.ui.pdf.AddEditPDF
import com.pedro.solutions.dialysisnotes.ui.pdf.PDFList
import com.pedro.solutions.dialysisnotes.ui.pdf.PDFViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    loginViewModel: LoginViewModel = koinViewModel(),
    dialysisViewModel: DialysisViewModel = koinViewModel(),
    pdfViewModel: PDFViewModel = koinViewModel()
) {
    val navController = rememberNavController()
    val startDestination: String =
        if (loginViewModel.isUserLoggedIn()) DialysisDestination.MainScreen.route else DialysisDestination.MainScreen.route

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

    var selectedItems = remember { mutableStateListOf<Int?>() }

    Scaffold(topBar = {
        if (selectedItems.isEmpty()) {
            TopAppBar(title = { Text(text = stringResource(R.string.app_name)) })
        } else {
            AppBarDelete(
                numSelectedItems = selectedItems.size
            ) {
                dialysisViewModel.onEvent(
                    AddEditDialysisEvent.OnDialysisListDeleted(
                        selectedItems.toList()
                    )
                )
                selectedItems.clear()
            }
        }
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
            navController = navController,
            startDestination = startDestination,
            enterTransition = {
                fadeIn(
                    tween(200)
                )
            },
            exitTransition = {
                fadeOut(tween(200))
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(700)
                )
            }
        ) {
            composable(
                DialysisDestination.AddEditDialysis.route + "/{userId}",
                arguments = listOf(navArgument("userId") {
                    type = NavType.IntType
                    defaultValue = -1
                })
            ) {
                selectedItems.clear()
                val id = it.arguments?.getInt("userId")
                dialysisViewModel.loadDialysisItem(id)
                AddEditDialysis(
                    dialysisViewModel,
                    onSaveOrDeleteButtonSelected = { destination ->
                        navController.navigate(destination.route)
                    })
            }
            composable(DialysisDestination.MainScreen.route) {
                DialysisList(
                    dialysisViewModel.dialysisList.observeAsState(initial = listOf()).value,
                    innerPadding,
                    onSelectedItemsChanged = { changedList ->
                        selectedItems.clear()
                        selectedItems.addAll(changedList)
                    },
                    onItemClicked = { itemId ->
                        navController.navigate(DialysisDestination.AddEditDialysis.route + "/$itemId")
                    })
            }
            composable(DialysisDestination.PDFList.route) {
                selectedItems.clear()
                pdfViewModel.resetState()
                PDFList(
                    pdfViewModel.allPDFsGenerated.observeAsState(initial = listOf()).value,
                    innerPadding
                )
            }
            composable(DialysisDestination.AddEditPDF.route + "/{PDFId}") {
                selectedItems.clear()
                AddEditPDF(pdfViewModel) {
                    navController.navigate(it.route)
                }
            }

            composable(DialysisDestination.LoginScreen.route) {
                selectedItems.clear()
                LoginScreen(loginViewModel = loginViewModel) {
                    navController.navigate(DialysisDestination.MainScreen.route)
                }
            }
        }
    }
}

@Composable
fun AppBarDelete(numSelectedItems: Int, onDeleteCLicked: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = pluralStringResource(
                R.plurals.selected_items,
                numSelectedItems,
                numSelectedItems
            )
        )
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = "",
            modifier = Modifier.clickable { onDeleteCLicked() })
    }
}

@Composable
@Preview
fun appBarDeletePreview() {
    AppBarDelete(numSelectedItems = 10, {})
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
            Icon(Icons.Filled.Add, stringResource(R.string.create_new_dialysis))
        }
    }
}