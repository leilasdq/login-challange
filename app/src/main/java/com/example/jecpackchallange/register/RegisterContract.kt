package com.example.jecpackchallange.register

data class RegisterState(
    val name: String = "",
    val nameError: String? = null,
    val site: String = "",
    val siteError: String? = null,
    val birthday: String = "",
    val emailItemList: List<RegisterItems> = listOf(RegisterItems("", "", true)),
    val phoneItemList: List<RegisterItems> = listOf(RegisterItems("", "", true)),
)

sealed class RegisterEvent {
    data class OnNameChanged(val newValue: String): RegisterEvent()
    data class OnEmailChanged(val index: Int, val newValue: String): RegisterEvent()
    data class OnEmailLabelChanged(val index: Int, val newValue: String): RegisterEvent()
    data class OnEmailPrimaryStateChanged(val index: Int, val newValue: Boolean): RegisterEvent()
    object OnAddEmail: RegisterEvent()
    data class OnEmailDeleted(val index: Int): RegisterEvent()
    data class OnPhoneChanged(val index: Int, val newValue: String): RegisterEvent()
    data class OnPhoneLabelChanged(val index: Int, val newValue: String): RegisterEvent()
    data class OnPhonePrimaryStateChanged(val index: Int, val newValue: Boolean): RegisterEvent()
    object OnAddPhone: RegisterEvent()
    data class OnPhoneDeleted(val index: Int): RegisterEvent()
    data class OnWebsiteChanged(val newValue: String): RegisterEvent()
    data class OnBirthDateChanged(val newValue: String): RegisterEvent()
    object OnRegisterClicked: RegisterEvent()
}

data class RegisterItems(
    val value: String,
    val label: String,
    var isPrimary: Boolean,
    val error: String? = null
)