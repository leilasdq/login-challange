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
                val list = _registerState.value.emailItemList.toMutableList()
                val item = list[eventType.index]
                list[eventType.index] = RegisterItems(eventType.newValue, item.label, item.isPrimary)
                _registerState.update { it.copy(emailItemList = list.toList()) }
            }
            is RegisterEvent.OnEmailLabelChanged -> {
                val list = _registerState.value.emailItemList.toMutableList()
                val item = list[eventType.index]
                list[eventType.index] = RegisterItems(item.value, eventType.newValue, item.isPrimary)
                _registerState.update { it.copy(emailItemList = list.toList()) }
            }
            is RegisterEvent.OnAddEmail -> {
                val list = _registerState.value.emailItemList.toMutableList()
                list.add(RegisterItems("", "", false))
                _registerState.update { it.copy(emailItemList = list.toList()) }
            }
            is RegisterEvent.OnEmailDeleted -> {
                val list = _registerState.value.emailItemList.toMutableList()
                list.removeAt(eventType.index)
                _registerState.update { it.copy(emailItemList = list.toList()) }
            }
            is RegisterEvent.OnEmailPrimaryStateChanged -> {
                val list = _registerState.value.emailItemList.toMutableList()
                list.forEach {
                    it.isPrimary = false
                }
                val item = list[eventType.index]
                list[eventType.index] = RegisterItems(item.value, item.label, eventType.newValue)
                _registerState.update { it.copy(emailItemList = list) }
            }
            is RegisterEvent.OnPhoneChanged -> {
                val list = _registerState.value.phoneItemList.toMutableList()
                val item = list[eventType.index]
                list[eventType.index] = RegisterItems(eventType.newValue, item.label, item.isPrimary)
                _registerState.update { it.copy(phoneItemList = list.toList()) }
            }
            is RegisterEvent.OnPhoneLabelChanged -> {
                val list = _registerState.value.phoneItemList.toMutableList()
                val item = list[eventType.index]
                list[eventType.index] = RegisterItems(item.value, eventType.newValue, item.isPrimary)
                _registerState.update { it.copy(phoneItemList = list.toList()) }
            }
            is RegisterEvent.OnAddPhone -> {
                val list = _registerState.value.phoneItemList.toMutableList()
                list.add(RegisterItems("", "", false))
                _registerState.update { it.copy(phoneItemList = list.toList()) }
            }
            is RegisterEvent.OnPhoneDeleted -> {
                val list = _registerState.value.phoneItemList.toMutableList()
                list.removeAt(eventType.index)
                _registerState.update { it.copy(phoneItemList = list.toList()) }
            }
            is RegisterEvent.OnPhonePrimaryStateChanged -> {
                val list = _registerState.value.phoneItemList.toMutableList()
                list.forEach {
                    it.isPrimary = false
                }
                val item = list[eventType.index]
                list[eventType.index] = RegisterItems(item.value, item.label, eventType.newValue)
                _registerState.update { it.copy(phoneItemList = list.toList()) }
            }
            is RegisterEvent.OnWebsiteChanged -> {
                _registerState.update { it.copy(site = eventType.newValue) }
            }
            is RegisterEvent.OnBirthDateChanged -> {
                _registerState.update { it.copy(birthday = eventType.newValue) }
            }
            is RegisterEvent.OnRegisterClicked -> {
                checkItems()
            }
        }
    }

    private fun checkItems() {
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

    private fun <T> updateLists(list: List<T>, index: Int, newValue: T): List<T> {
        return list.toMutableList().replace(list[index], newValue).toList()
    }

    private fun <E> Iterable<E>.replace(old: E, new: E) = map { if (it == old) new else it }
}