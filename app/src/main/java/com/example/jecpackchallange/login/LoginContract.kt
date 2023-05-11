package com.example.jecpackchallange.login

data class LoginState(
    val name: String = "",
    val site: String = "",
    val birthday: String = "",
    val emailItemList: List<LoginItems> = listOf(LoginItems("", "", false)),
    val phoneItemList: List<LoginItems> = listOf(LoginItems("", "", false)),
)

sealed class LoginEvent {
    data class OnNameChanged(val newValue: String): LoginEvent()
    data class OnEmailChanged(val index: Int, val newValue: String): LoginEvent()
    object OnAddEmail: LoginEvent()
    data class OnEmailDeleted(val index: Int): LoginEvent()
    data class OnEmailLabelChanged(val index: Int, val newValue: String): LoginEvent()
    data class OnPhoneChanged(val index: Int, val newValue: String): LoginEvent()
    object OnAddPhone: LoginEvent()
    data class OnPhoneDeleted(val index: Int): LoginEvent()
    data class OnPhoneLabelChanged(val index: Int, val newValue: String): LoginEvent()
    data class OnWebsiteChanged(val newValue: String): LoginEvent()
    data class OnBirthDateChanged(val newValue: String): LoginEvent()
    object OnRegisterClicked: LoginEvent()
}

data class LoginItems(
    val value: String,
    val label: String,
    val isPrimary: Boolean
)