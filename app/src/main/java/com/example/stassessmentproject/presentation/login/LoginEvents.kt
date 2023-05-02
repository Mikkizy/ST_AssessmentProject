package com.example.stassessmentproject.presentation.login

sealed class LoginEvents {
    data class EnteredUsername(val username: String): LoginEvents()
    object ClickedLoginButton: LoginEvents()
}
