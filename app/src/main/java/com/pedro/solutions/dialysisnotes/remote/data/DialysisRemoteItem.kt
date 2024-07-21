package com.pedro.solutions.dialysisnotes.remote.data

data class DialysisRemoteItem(
    val created_at: Int,
    val final_uf: Int,
    val id: Int,
    val initial_uf: Int,
    val notes: String,
    val updated_at: Int
)