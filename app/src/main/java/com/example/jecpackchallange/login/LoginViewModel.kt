package com.example.jecpackchallange.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(): ViewModel() {

    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState

    fun onTriggerEvent(eventType: LoginEvent) {
        when (eventType) {
            is LoginEvent.OnNameChanged -> {
                _loginState.update { it.copy(name = eventType.newValue) }
            }
            is LoginEvent.OnEmailChanged -> {
                val list = _loginState.value.emailItemList.toMutableList()
                val item = list[eventType.index]
                list[eventType.index] = LoginItems(eventType.newValue, item.label, item.isPrimary)
                _loginState.update { it.copy(emailItemList = list.toList()) }
            }
            is LoginEvent.OnEmailLabelChanged -> {
                val list = _loginState.value.emailItemList.toMutableList()
                val item = list[eventType.index]
                list[eventType.index] = LoginItems(item.value, eventType.newValue, item.isPrimary)
                _loginState.update { it.copy(emailItemList = list.toList()) }
            }
            is LoginEvent.OnAddEmail -> {
                val list = _loginState.value.emailItemList.toMutableList()
                list.add(LoginItems("", "", false))
                _loginState.update { it.copy(emailItemList = list.toList()) }
            }
            is LoginEvent.OnEmailDeleted -> {
                val list = _loginState.value.emailItemList.toMutableList()
                list.removeAt(eventType.index)
                _loginState.update { it.copy(emailItemList = list.toList()) }
            }
            is LoginEvent.OnEmailPrimaryStateChanged -> {
                val list = _loginState.value.emailItemList.toMutableList()
                list.forEach {
                    it.isPrimary = false
                }
                val item = list[eventType.index]
                list[eventType.index] = LoginItems(item.value, item.label, eventType.newValue)
                _loginState.update { it.copy(emailItemList = list) }
            }
            is LoginEvent.OnPhoneChanged -> {
                val list = _loginState.value.phoneItemList.toMutableList()
                val item = list[eventType.index]
                list[eventType.index] = LoginItems(eventType.newValue, item.label, item.isPrimary)
                _loginState.update { it.copy(phoneItemList = list.toList()) }
            }
            is LoginEvent.OnPhoneLabelChanged -> {
                val list = _loginState.value.phoneItemList.toMutableList()
                val item = list[eventType.index]
                list[eventType.index] = LoginItems(item.value, eventType.newValue, item.isPrimary)
                _loginState.update { it.copy(phoneItemList = list.toList()) }
            }
            is LoginEvent.OnAddPhone -> {
                val list = _loginState.value.phoneItemList.toMutableList()
                list.add(LoginItems("", "", false))
                _loginState.update { it.copy(phoneItemList = list.toList()) }
            }
            is LoginEvent.OnPhoneDeleted -> {
                val list = _loginState.value.phoneItemList.toMutableList()
                list.removeAt(eventType.index)
                _loginState.update { it.copy(phoneItemList = list.toList()) }
            }
            is LoginEvent.OnPhonePrimaryStateChanged -> {
                val list = _loginState.value.phoneItemList.toMutableList()
                list.forEach {
                    it.isPrimary = false
                }
                val item = list[eventType.index]
                list[eventType.index] = LoginItems(item.value, item.label, eventType.newValue)
                _loginState.update { it.copy(phoneItemList = list.toList()) }
            }
            is LoginEvent.OnWebsiteChanged -> {
                _loginState.update { it.copy(site = eventType.newValue) }
            }
            is LoginEvent.OnBirthDateChanged -> {
                _loginState.update { it.copy(birthday = eventType.newValue) }
            }
            is LoginEvent.OnRegisterClicked -> {
                checkItems()
            }
        }
    }

    private fun checkItems() {

    }

    private fun <T> updateLists(list: List<T>, index: Int, newValue: T): List<T> {
        return list.toMutableList().replace(list[index], newValue).toList()
    }

    private fun <E> Iterable<E>.replace(old: E, new: E) = map { if (it == old) new else it }
}