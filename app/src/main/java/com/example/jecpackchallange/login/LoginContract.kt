package com.example.jecpackchallange.login

data class LoginState(
    val name: String = "",
    val email: List<String> = listOf(),
    val emailLabel: List<String> = listOf(),
    val phone: List<String> = listOf(),
    val phoneLabel: List<String> = listOf(),
    val site: String = "",
    // todo: add birth day
)

sealed class LoginEvent {
    data class OnNameChanged(val newValue: String): LoginEvent()
    data class OnEmailChanged(val index: Int, val newValue: String): LoginEvent()
    object OnAddEmail: LoginEvent()
    data class OnEmailLabelChanged(val index: Int, val newValue: String): LoginEvent()
    data class OnPhoneChanged(val index: Int, val newValue: String): LoginEvent()
    object OnAddPhone: LoginEvent()
    data class OnPhoneLabelChanged(val index: Int, val newValue: String): LoginEvent()
    data class OnWebsiteChanged(val newValue: String): LoginEvent()

    // todo: add birthday change and on error occurred
}