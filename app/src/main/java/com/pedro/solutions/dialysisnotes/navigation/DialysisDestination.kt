package com.pedro.solutions.dialysisnotes.navigation

sealed class DialysisDestination(val route: String) {
    data object MainScreen : DialysisDestination("mainScreen")
    data object AddEditDialysis : DialysisDestination("addEditDialysis")
}
