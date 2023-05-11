package com.example.jecpackchallange.login

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jecpackchallange.utils.compose.ChallengeTextField
import com.example.jecpackchallange.utils.compose.LabelTextField
import com.google.android.material.datepicker.MaterialDatePicker
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun LoginScreen(
    viewModel: LoginViewModel
) {
    val uiState by viewModel.loginState.collectAsState()

    LoginScreenContent(
        name = uiState.name,
        onNameChanged = {
            viewModel.onTriggerEvent(LoginEvent.OnNameChanged(it))
        },
        emailList = uiState.emailItemList,
        onEmailChanged = { index, email ->
            viewModel.onTriggerEvent(LoginEvent.OnEmailChanged(index, email))
        },
        onEmailLabelChanged = { index, label ->
            viewModel.onTriggerEvent(LoginEvent.OnEmailLabelChanged(index, label))
        },
        onAddEmail = {
            viewModel.onTriggerEvent(LoginEvent.OnAddEmail)
        },
        onDeleteEmail = {
            viewModel.onTriggerEvent(LoginEvent.OnEmailDeleted(it))
        },
        phoneList = uiState.phoneItemList,
        onPhoneChanged = { index, phone ->
            viewModel.onTriggerEvent(LoginEvent.OnPhoneChanged(index, phone))
        },
        onPhoneLabelChanged = { index, label ->
            viewModel.onTriggerEvent(LoginEvent.OnPhoneLabelChanged(index, label))
        },
        onAddPhone = { viewModel.onTriggerEvent(LoginEvent.OnAddPhone) },
        onDeletePhone = {
            viewModel.onTriggerEvent(LoginEvent.OnPhoneDeleted(it))
        },
        website = uiState.site,
        onWebsiteChanged = {
            viewModel.onTriggerEvent(LoginEvent.OnWebsiteChanged(it))
        },
        onRegisterClicked = {
            viewModel.onTriggerEvent(LoginEvent.OnRegisterClicked)
        },
        fullDate = uiState.birthday,
        onDateChanged = {
            viewModel.onTriggerEvent(LoginEvent.OnBirthDateChanged(it))
        }
    )
}

@Composable
private fun LoginScreenContent(
    name: String,
    onNameChanged: (String) -> Unit,
    emailList: List<LoginItems>,
    onEmailChanged: (Int, String) -> Unit,
    onEmailLabelChanged: (Int, String) -> Unit,
    onAddEmail: () -> Unit,
    onDeleteEmail: (Int) -> Unit,
    phoneList: List<LoginItems>,
    onPhoneChanged: (Int, String) -> Unit,
    onPhoneLabelChanged: (Int, String) -> Unit,
    onAddPhone: () -> Unit,
    onDeletePhone: (Int) -> Unit,
    website: String,
    onWebsiteChanged: (String) -> Unit,
    onRegisterClicked: () -> Unit,
    fullDate: String,
    onDateChanged: (String) -> Unit
) {

    val focusManager = LocalFocusManager.current
    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("MMM dd yyyy")
                .format(pickedDate)
        }
    }
    val dateDialogState = rememberMaterialDialogState()

    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(text = "Ok") {
                onDateChanged(formattedDate)
            }
            negativeButton(text = "Cancel")
        }
    ) {
        datepicker(
            initialDate = LocalDate.now(),
            title = "Pick a date",
        ) {
            pickedDate = it
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Register",
                style = MaterialTheme.typography.h4
            )
            Button(
                shape = RoundedCornerShape(50),
                onClick = { onRegisterClicked() },
            ) {
                Text(text = "Submit")
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
        ChallengeTextField(
            label = "name",
            value = name,
            onValueChange = { onNameChanged(it) },
            icon = Icons.Outlined.Person,
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(
                        FocusDirection.Down
                    )
                }),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            ),
        )
        emailList.forEachIndexed { index, loginItem ->
            LabelTextField(
                label = "email",
                itemValue = loginItem.value,
                itemLabel = loginItem.label,
                onItemValueChanged = {
                    onEmailChanged(index, it)
                },
                onItemLabelChanged = {
                    onEmailLabelChanged.invoke(index, it)
                },
                isPrimary = false,
                onAddItem = onAddEmail,
                onDeleteItem = {
                    onDeleteEmail(index)
                },
                onSelectAsPrimaryClicked = {},
                icon = Icons.Outlined.Email,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                ),
                focusManager = focusManager,
                showDeleteIcon = emailList.size > 1
            )
        }
        phoneList.forEachIndexed { index, loginItem ->
            LabelTextField(
                label = "phone",
                itemValue = loginItem.value,
                itemLabel = loginItem.label,
                onItemValueChanged = {
                    onPhoneChanged(index, it)
                },
                onItemLabelChanged = {
                    onPhoneLabelChanged.invoke(index, it)
                },
                isPrimary = false,
                onAddItem = onAddPhone,
                onDeleteItem = {
                    onDeletePhone(index)
                },
                onSelectAsPrimaryClicked = {},
                icon = Icons.Outlined.Phone,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Phone
                ),
                focusManager = focusManager,
                showDeleteIcon = phoneList.size > 1
            )
        }
        ChallengeTextField(
            label = "website",
            value = website,
            onValueChange = {onWebsiteChanged(it) },
            icon = Icons.Outlined.Link,
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.clearFocus()
                }),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Uri
            )
        )
        ChallengeTextField(
            label = "birthday",
            value = fullDate,
            onValueChange = { },
            readOnly = true,
            enable = false,
            modifier = Modifier.clickable {
                dateDialogState.show()
            },
            icon = Icons.Outlined.DateRange,
            trailingIcon = {
                Icon(
                    imageVector =
                        if (dateDialogState.showing) Icons.Outlined.KeyboardArrowUp
                        else Icons.Outlined.KeyboardArrowDown,
                    contentDescription = null
                )
            }
        )
    }
}

private fun showDatePicker(
    activity : AppCompatActivity,
    updatedDate: (Long?) -> Unit)
{
    val picker = MaterialDatePicker.Builder.datePicker().build()
    picker.show(activity.supportFragmentManager, picker.toString())
    picker.addOnPositiveButtonClickListener {
        updatedDate(it)
    }
}

@Preview
@Composable
private fun Prev() {
    LoginScreenContent(
        name = "Leila", onNameChanged = {},
        emailList = listOf(LoginItems("test@gmail.com", "test", false)),
        onEmailChanged = { _, _ -> Unit }, onEmailLabelChanged = { _, _ -> Unit },
        onAddEmail = {},
        phoneList = listOf(LoginItems("111", "testtt", true)),
        onPhoneChanged = { _, _ -> Unit }, onPhoneLabelChanged = { _, _ -> Unit },
        onAddPhone = {},
        website = "test.com", onWebsiteChanged = {},
        onRegisterClicked = {},
        fullDate = "1390/9/1", onDateChanged = {},
        onDeleteEmail = {}, onDeletePhone = {}
    )
}