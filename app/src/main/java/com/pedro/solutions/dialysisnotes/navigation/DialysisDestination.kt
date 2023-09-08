package com.pedro.solutions.dialysisnotes.navigation

interface DialysisDestination {
    val route: String
}

object MainScreen: DialysisDestination {
    override val route: String
        get() = "mainScreen"
}

object AddEditDialysis: DialysisDestination {
    override val route: String
        get() = "addEditDialysis"
}