package com.example.jecpackchallange.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel(): ViewModel() {

    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState

    fun onTriggerEvent(eventType: LoginEvent) {
        when (eventType) {
            is LoginEvent.OnNameChanged -> {
                _loginState.update { it.copy(name = eventType.newValue) }
            }
            is LoginEvent.OnEmailChanged -> {
                val emailList = updateLists(_loginState.value.email, eventType.index, eventType.newValue)
                _loginState.update { it.copy(email = emailList.toList()) }
            }
            is LoginEvent.OnEmailLabelChanged -> {
                val emailLabelList = updateLists(_loginState.value.emailLabel, eventType.index, eventType.newValue)
                _loginState.update { it.copy(emailLabel = emailLabelList.toList()) }
            }
            is LoginEvent.OnAddEmail -> {
                val emailList = _loginState.value.email.toMutableList()
                val emailLabelList = _loginState.value.emailLabel.toMutableList()
                emailList.add("")
                emailLabelList.add("")
                _loginState.update { it.copy(emailLabel = emailLabelList, email = emailList) }
            }
            is LoginEvent.OnPhoneChanged -> {
                val phoneList = updateLists(_loginState.value.phone, eventType.index, eventType.newValue)
                _loginState.update { it.copy(phone = phoneList.toList()) }
            }
            is LoginEvent.OnPhoneLabelChanged -> {
                val phoneLabelList = updateLists(_loginState.value.phoneLabel, eventType.index, eventType.newValue)
                _loginState.update { it.copy(phoneLabel = phoneLabelList.toList()) }
            }
            is LoginEvent.OnAddPhone -> {
                val phoneList = _loginState.value.phone.toMutableList()
                val phoneLabelList = _loginState.value.phoneLabel.toMutableList()
                phoneList.add("")
                phoneLabelList.add("")
                _loginState.update { it.copy(phoneLabel = phoneLabelList, phone = phoneList) }
            }
            is LoginEvent.OnWebsiteChanged -> {
                _loginState.update { it.copy(site = eventType.newValue) }
            }
        }
    }

    private fun <T> updateLists(list: List<T>, index: Int, newValue: T): List<T> {
        return list.toMutableList().replace(list[index], newValue).toList()
    }

    private fun <E> Iterable<E>.replace(old: E, new: E) = map { if (it == old) new else it }
}