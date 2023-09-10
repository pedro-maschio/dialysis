package com.pedro.solutions.dialysisnotes.ui.pdf_generator

data class AddEditPDFState(
    val patient: String = "",
    val startInterval: Long = 0,
    val endInterval: Long = 0,
    val fileDirectory: String = "",
    val isDateSelectable: (Long) -> Boolean
)