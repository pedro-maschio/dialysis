package com.pedro.solutions.dialysisnotes.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.pedro.solutions.dialysisnotes.R
import com.pedro.solutions.dialysisnotes.ui.theme.CommonScaffold

@Composable
fun LoginScreen(loginViewModel: LoginViewModel, onLogin: () -> Unit) {
    val uiState by loginViewModel.uiState.collectAsState()

    if (uiState.isLoggedIn) {
        onLogin()
    }
    CommonScaffold(screenTitle = stringResource(R.string.login_title)) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(0.dp, 50.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(0.9f),
                value = uiState.email,
                onValueChange = { newEmail ->
                    loginViewModel.onEvent(
                        LoginEvent.OnEmailChangedEvent(
                            newEmail
                        )
                    )
                },
                label = {
                    Text(text = stringResource(id = R.string.email_placeholder))
                },
                maxLines = 1,
                textStyle = TextStyle(MaterialTheme.colors.primary),
                isError = !uiState.isEmailValid
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(0.9f),
                value = uiState.password,
                onValueChange = { newPassword ->
                    loginViewModel.onEvent(
                        LoginEvent.OnPasswordChangedEvent(
                            newPassword
                        )
                    )
                },
                label = {
                    Text(text = stringResource(id = R.string.password_placeholder))
                },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                textStyle = TextStyle(color = Color.White, fontWeight = FontWeight.Bold),
                isError = !uiState.isPasswordValid,
            )
            if (!uiState.isPasswordValid) {
                Text(text = stringResource(id = R.string.password_error), color = Color.Red)
            }

            Button(modifier = Modifier.padding(0.dp, 20.dp), onClick = {
                loginViewModel.onEvent(LoginEvent.OnSwitchTabsEvent(isCreating = false))
                loginViewModel.onEvent(LoginEvent.OnLogin())
            }) {
                Text(text = stringResource(id = R.string.login_title))
            }
            Button(modifier = Modifier.padding(0.dp, 20.dp), onClick = {
                loginViewModel.onEvent(LoginEvent.OnSwitchTabsEvent(isCreating = true))
                loginViewModel.onEvent(LoginEvent.OnUserCreatedEvent())
            }) {
                Text(text = stringResource(id = R.string.register_title))
            }
        }
    }
}