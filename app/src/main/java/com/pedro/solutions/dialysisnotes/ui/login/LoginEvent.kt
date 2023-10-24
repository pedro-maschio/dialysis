package com.pedro.solutions.dialysisnotes.ui.login

sealed class LoginEvent {
    class OnEmailChangedEvent(val email: String): LoginEvent()

    class OnPasswordChangedEvent(val password: String): LoginEvent()

    class OnSwitchTabsEvent(val isCreating: Boolean): LoginEvent()

    class OnUserCreatedEvent(): LoginEvent()

    class OnLogin(): LoginEvent()
}