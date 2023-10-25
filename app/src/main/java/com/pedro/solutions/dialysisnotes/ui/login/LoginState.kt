package com.pedro.solutions.dialysisnotes.ui.login

data class LoginState(
    val createdAt: Long = 0,
    val updatedAat: Long = 0,
    val email: String = "",
    val password: String = "",
    val isCreating: Boolean = false,
    val isLoggedIn: Boolean = false,
    val isEmailValid: Boolean = true,
    val isPasswordValid: Boolean = true
)