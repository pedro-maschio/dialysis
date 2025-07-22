package com.pedro.solutions.dialysisnotes.ui.pdf

data class AddEditPDFState(
    val patient: String = "",
    val startInterval: Long = 0,
    val endInterval: Long = 0,
    val fileDirectory: String = "",
    val isDateSelectableStartInterval: (Long) -> Boolean,
    val isDateSelectableEndInterval: (Long) -> Boolean
)