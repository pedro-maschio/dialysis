package com.pedro.solutions.dialysisnotes.ui.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedro.solutions.dialysisnotes.DialysisApplication
import com.pedro.solutions.dialysisnotes.data.users.User
import com.pedro.solutions.dialysisnotes.data.users.UserDao
import com.pedro.solutions.dialysisnotes.utils.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class LoginViewModel(
    val dao: UserDao,
    private val application: DialysisApplication,
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginState())

    val uiState: StateFlow<LoginState> = _uiState.asStateFlow()


    fun isUserLoggedIn(): Boolean {
        return application.getSharedPreferences(
            Constants.DIALYSIS_PREFERENCES, Context.MODE_PRIVATE
        ).getBoolean(
            Constants.LOGGED_IN, false
        )
    }

    private fun setUserLoggedIn() {
        application.getSharedPreferences(Constants.DIALYSIS_PREFERENCES, Context.MODE_PRIVATE)
            .edit().putBoolean(
                Constants.LOGGED_IN, true
            ).apply()
    }

    private fun createUser() {
        viewModelScope.launch {
            val u = User(
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                _uiState.value.email,
                _uiState.value.password,
                null
            )
            dao.createUser(u)
            _uiState.update { currentState ->
                currentState.copy(isLoggedIn = true)
            }
            setUserLoggedIn()
        }
    }

    private fun tryToLogin() {
        viewModelScope.launch {
            val user: User? =
                dao.findUserByEmailAndPassword(_uiState.value.email, _uiState.value.password)

            user?.let {
                _uiState.update { currentState ->
                    currentState.copy(isLoggedIn = true)
                }
                setUserLoggedIn()
            }
        }
    }

    fun onEvent(event: LoginEvent) {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        when (event) {
            is LoginEvent.OnEmailChangedEvent -> {
                _uiState.update { currentState ->
                    currentState.copy(
                        email = event.email,
                        isEmailValid = event.email.matches(emailRegex.toRegex())
                    )
                }
            }

            is LoginEvent.OnPasswordChangedEvent -> {
                _uiState.update { currentState ->
                    currentState.copy(password = event.password, isPasswordValid = event.password.length >= 5)
                }
            }

            is LoginEvent.OnSwitchTabsEvent -> {
                _uiState.update { currentState ->
                    currentState.copy(isCreating = event.isCreating)
                }
            }

            is LoginEvent.OnUserCreatedEvent -> {
                createUser()
            }

            is LoginEvent.OnLogin -> {
                tryToLogin()
            }
        }
    }
}