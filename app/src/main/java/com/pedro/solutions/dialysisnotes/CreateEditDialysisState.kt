package com.pedro.solutions.dialysisnotes

data class CreateEditDialysisState(
    val createdAt: Long = 0,
    val initialUF: String = "",
    val finalUF: String = "",
    val observations: String = "",
    val isEditing: Boolean = false,
    val id: Int? = null
)