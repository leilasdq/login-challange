package com.example.jecpackchallange.register

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.jecpackchallange.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    @ApplicationContext val context: Context
): ViewModel() {

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
        val birthdayError = registerState.value.birthdayError

        return if (nameError != null || siteError != null || birthdayError != null) {
            true
        }
        else (emailList.any { it.error != null } || phoneList.any { it.error != null })
    }


    private fun checkForErrors() {
        val emailList = registerState.value.emailItemList.toMutableList()
        val phoneList = registerState.value.phoneItemList.toMutableList()
        val name = registerState.value.name
        val site = registerState.value.site
        val birthday = registerState.value.birthday

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
            birthdayError = messageBaseBirthdayValidation(birthday)
        ) }
    }

    private fun messageBaseOnEmailValidation(email: String): String? {
        return if (email.isNullOrEmpty()) context.getString(R.string.error_empty_item)
        else if (android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches().not()) context.getString(R.string.error_not_in_format, "email")
        else null
    }

    private fun messageBaseOnWebsiteValidation(site: String): String? {
        return if (site.isNullOrEmpty()) context.getString(R.string.error_empty_item)
        else if (android.util.Patterns.WEB_URL.matcher(site.trim()).matches().not()) context.getString(R.string.error_not_in_format, "url link")
        else null
    }

    private fun messageBaseOnPhoneValidation(phone: String): String? {
        return if (phone.isNullOrEmpty()) context.getString(R.string.error_empty_item)
        else if (phone.length < 11) context.getString(R.string.error_phone_short)
        else if (android.util.Patterns.PHONE.matcher(phone.trim()).matches().not()) context.getString(R.string.error_not_in_format, "phone")
        else null
    }

    private fun messageBaseOnNameValidation(name: String): String? {
        return if (name.isNullOrEmpty()) context.getString(R.string.error_empty_item)
        else if (name.length < 5) context.getString(R.string.error_short)
        else null
    }

    private fun messageBaseBirthdayValidation(birthday: String): String? {
        val splittedDate = birthday.split(' ')
        return if (birthday.isNullOrEmpty()) context.getString(R.string.error_empty_item)
        else if (splittedDate.last().toInt() > Calendar.getInstance().get(Calendar.YEAR) - 3) context.getString(R.string.error_birthdate_short)
        else if (splittedDate.last().toInt() >= Calendar.getInstance().get(Calendar.YEAR) - 15) context.getString(R.string.error_minimum_birthdate)
        else null
    }
}