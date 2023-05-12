package com.example.jecpackchallange.register

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(): ViewModel() {

    private val _registerState = MutableStateFlow(RegisterState())
    val registerState = _registerState

    fun onTriggerEvent(eventType: RegisterEvent) {
        when (eventType) {
            is RegisterEvent.OnNameChanged -> {
                _registerState.update { it.copy(name = eventType.newValue) }
            }
            is RegisterEvent.OnEmailChanged -> {
                _registerState.update {
                    it.copy(
                        emailItemList = updateRegisterValueItem(
                            _registerState.value.emailItemList.toMutableList(),
                            eventType.index,
                            eventType.newValue
                        )
                    )
                }
            }
            is RegisterEvent.OnEmailLabelChanged -> {
                _registerState.update {
                    it.copy(
                        emailItemList = updateRegisterLabelItem(
                            _registerState.value.emailItemList.toMutableList(),
                            eventType.index,
                            eventType.newValue
                        )
                    )
                }
            }
            is RegisterEvent.OnAddEmail -> {
                _registerState.update { it.copy(emailItemList = addItemToReturnedList(_registerState.value.emailItemList.toMutableList())) }
            }
            is RegisterEvent.OnEmailDeleted -> {
                _registerState.update {
                    it.copy(
                        emailItemList = removeItemInReturnedList(
                            _registerState.value.emailItemList.toMutableList(),
                            eventType.index
                        )
                    )
                }
            }
            is RegisterEvent.OnEmailPrimaryStateChanged -> {
                _registerState.update {
                    it.copy(
                        emailItemList = updateRegisterPrimaryItem(
                            _registerState.value.emailItemList.toMutableList(),
                            eventType.index,
                            eventType.newValue
                        )
                    )
                }
            }
            is RegisterEvent.OnPhoneChanged -> {
                _registerState.update {
                    it.copy(
                        phoneItemList = updateRegisterValueItem(
                            _registerState.value.phoneItemList.toMutableList(),
                            eventType.index,
                            eventType.newValue
                        )
                    )
                }
            }
            is RegisterEvent.OnPhoneLabelChanged -> {
                _registerState.update {
                    it.copy(
                        phoneItemList = updateRegisterLabelItem(
                            _registerState.value.phoneItemList.toMutableList(),
                            eventType.index,
                            eventType.newValue
                        )
                    )
                }
            }
            is RegisterEvent.OnAddPhone -> {
                _registerState.update { it.copy(phoneItemList = addItemToReturnedList(_registerState.value.phoneItemList.toMutableList())) }
            }
            is RegisterEvent.OnPhoneDeleted -> {
                _registerState.update {
                    it.copy(
                        phoneItemList = removeItemInReturnedList(
                            _registerState.value.phoneItemList.toMutableList(),
                            eventType.index
                        )
                    )
                }
            }
            is RegisterEvent.OnPhonePrimaryStateChanged -> {
                _registerState.update {
                    it.copy(
                        phoneItemList = updateRegisterPrimaryItem(
                            _registerState.value.phoneItemList.toMutableList(),
                            eventType.index,
                            eventType.newValue
                        )
                    )
                }
            }
            is RegisterEvent.OnWebsiteChanged -> {
                _registerState.update { it.copy(site = eventType.newValue) }
            }
            is RegisterEvent.OnBirthDateChanged -> {
                _registerState.update { it.copy(birthday = eventType.newValue) }
            }
            is RegisterEvent.OnRegisterClicked -> {
                showSuccessIfNoErrorHappened()
            }
            is RegisterEvent.ResetSuccessState -> {
                _registerState.update { it.copy(success = false) }
            }
        }
    }

    private fun updateRegisterValueItem(list: MutableList<RegisterItems>, index: Int, value: String): List<RegisterItems> {
        val item = list[index]
        list[index] = RegisterItems(value, item.label, item.isPrimary)
        return list
    }

    private fun updateRegisterLabelItem(list: MutableList<RegisterItems>, index: Int, value: String): List<RegisterItems> {
        val item = list[index]
        list[index] = RegisterItems(item.value, value, item.isPrimary)
        return list
    }

    private fun updateRegisterPrimaryItem(list: MutableList<RegisterItems>, index: Int, value: Boolean): List<RegisterItems> {
        list.forEach {
            it.isPrimary = false
        }
        val item = list[index]
        list[index] = RegisterItems(item.value, item.label, value)

        return list
    }

    private fun addItemToReturnedList(list: MutableList<RegisterItems>): List<RegisterItems> {
        list.add(RegisterItems("", "", false))
        return list
    }

    private fun removeItemInReturnedList(list: MutableList<RegisterItems>, index: Int): List<RegisterItems> {
        list.removeAt(index)
        return list
    }

    private fun showSuccessIfNoErrorHappened() {
        checkForErrors()
        if (hasItemsError().not()) {
            _registerState.update {
                it.copy(success = true)
            }
        }
    }

    private fun hasItemsError(): Boolean {
        val emailList = registerState.value.emailItemList.toMutableList()
        val phoneList = registerState.value.phoneItemList.toMutableList()
        val nameError = registerState.value.nameError
        val siteError = registerState.value.siteError

        return if (nameError != null || siteError != null) {
            true
        }
        else (emailList.any { it.error != null } || phoneList.any { it.error != null })
    }


    private fun checkForErrors() {
        val emailList = registerState.value.emailItemList.toMutableList()
        val phoneList = registerState.value.phoneItemList.toMutableList()
        val name = registerState.value.name
        val site = registerState.value.site

        emailList.forEachIndexed { index, items ->
            val msg = messageBaseOnEmailValidation(items.value)
            if (msg != null)
                emailList[index] = RegisterItems(items.value, items.label, items.isPrimary, msg)
        }
        phoneList.forEachIndexed { index, items ->
            val msg = messageBaseOnPhoneValidation(items.value)
            if (msg != null)
                phoneList[index] = RegisterItems(items.value, items.label, items.isPrimary, msg)
        }

        _registerState.update { it.copy(
            emailItemList = emailList,
            phoneItemList = phoneList,
            nameError = messageBaseOnNameValidation(name),
            siteError = messageBaseOnWebsiteValidation(site),
        ) }
    }

    private fun messageBaseOnEmailValidation(email: String): String? {
        return if (email.isNullOrEmpty()) "can't be empty"
        else if (android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches().not()) "email format is not correct"
        else null
    }

    private fun messageBaseOnWebsiteValidation(site: String): String? {
        return if (site.isNullOrEmpty()) "can't be empty"
        else if (android.util.Patterns.WEB_URL.matcher(site.trim()).matches().not()) "url link format is not correct"
        else null
    }

    private fun messageBaseOnPhoneValidation(phone: String): String? {
        return if (phone.isNullOrEmpty()) "can't be empty"
        else if (phone.length < 11) "phone is 11 character!"
        else if (android.util.Patterns.PHONE.matcher(phone.trim()).matches().not()) "phone format is not correct"
        else null
    }

    private fun messageBaseOnNameValidation(name: String): String? {
        return if (name.isNullOrEmpty()) "can't be empty"
        else if (name.length < 5) "too short!"
        else null
    }
}