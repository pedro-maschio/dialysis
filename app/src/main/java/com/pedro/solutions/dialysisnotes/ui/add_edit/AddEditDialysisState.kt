package com.pedro.solutions.dialysisnotes.ui.add_edit

data class AddEditDialysisState(
    val createdAt: Long = 0,
    val initialUF: String = "",
    val finalUF: String = "",
    val observations: String = "",
    val isEditing: Boolean = false,
    val id: Int? = null,
    val initialUFInvalid: Boolean = false,
    val finalUFInvalid: Boolean = false,
    val observationsInvalid: Boolean = false,
)