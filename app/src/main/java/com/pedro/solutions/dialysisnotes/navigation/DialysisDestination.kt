package com.pedro.solutions.dialysisnotes.navigation

import androidx.annotation.StringRes
import com.pedro.solutions.dialysisnotes.R

//  TODO: use resourceId to set the screen titles
sealed class DialysisDestination(
    val route: String,
    @StringRes val resourceId: Int,
) {
    data object MainScreen : DialysisDestination("mainScreen", R.string.app_name)
    data object AddEditDialysis :
        DialysisDestination("addEditDialysis", R.string.create_new_dialysis)

    data object PDFList : DialysisDestination("pdfList", R.string.pdfs_gerados)
}
