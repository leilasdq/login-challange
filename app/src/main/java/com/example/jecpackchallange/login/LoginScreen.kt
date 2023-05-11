package com.example.jecpackchallange.login

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        emailList = uiState.email,
        emailLabelList = uiState.emailLabel,
        onEmailChanged = { index, email ->
            viewModel.onTriggerEvent(LoginEvent.OnEmailChanged(index, email))
        },
        onEmailLabelChanged = { index, label ->
            viewModel.onTriggerEvent(LoginEvent.OnEmailLabelChanged(index, label))
        },
        onAddEmail = {
            viewModel.onTriggerEvent(LoginEvent.OnAddEmail)
        },
        phoneList = uiState.phone,
        phoneLabelList = uiState.phoneLabel,
        onPhoneChanged = { index, phone ->
            viewModel.onTriggerEvent(LoginEvent.OnPhoneChanged(index, phone))
        },
        onPhoneLabelChanged = { index, label ->
            viewModel.onTriggerEvent(LoginEvent.OnPhoneLabelChanged(index, label))
        },
        onAddPhone = { viewModel.onTriggerEvent(LoginEvent.OnAddPhone) },
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
    emailList: List<String>,
    emailLabelList: List<String>,
    onEmailChanged: (Int, String) -> Unit,
    onEmailLabelChanged: (Int, String) -> Unit,
    onAddEmail: () -> Unit,
    phoneList: List<String>,
    phoneLabelList: List<String>,
    onPhoneChanged: (Int, String) -> Unit,
    onPhoneLabelChanged: (Int, String) -> Unit,
    onAddPhone: () -> Unit,
    website: String,
    onWebsiteChanged: (String) -> Unit,
    onRegisterClicked: () -> Unit,
    fullDate: String,
    onDateChanged: (String) -> Unit
) {
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
            .padding(8.dp),
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
            icon = Icons.Outlined.Person
        )
        LabelTextField(
            label = "email",
            itemValue = if (emailList.isEmpty().not()) emailList[0] else "",
            itemLabel = if (emailLabelList.isEmpty().not()) emailLabelList[0] else "",
            onItemValueChanged = {
                onEmailChanged(0, it)
            },
            onItemLabelChanged = {
                onEmailLabelChanged.invoke(0, it)
            },
            isPrimary = false,
            onAddItem = onAddEmail,
            onDeleteItem = {},
            onSelectAsPrimaryClicked = {},
            icon = Icons.Outlined.Email
        )
        LabelTextField(
            label = "phone",
            itemValue = if (phoneList.isEmpty().not()) phoneList[0] else "",
            itemLabel = if (phoneLabelList.isEmpty().not()) phoneLabelList[0] else "",
            onItemValueChanged = {
                onPhoneChanged(0, it)
            },
            onItemLabelChanged = {
                onPhoneLabelChanged.invoke(0, it)
            },
            isPrimary = false,
            onAddItem = onAddPhone,
            onDeleteItem = {},
            onSelectAsPrimaryClicked = {},
            icon = Icons.Outlined.Phone
        )
        ChallengeTextField(
            label = "website",
            value = website,
            onValueChange = {onWebsiteChanged(it) },
            icon = Icons.Outlined.Link
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
        emailList = listOf("test@gmail.com"), emailLabelList = listOf("test"),
        onEmailChanged = { _, _ -> Unit }, onEmailLabelChanged = { _, _ -> Unit },
        onAddEmail = {},
        phoneList = listOf("111"), phoneLabelList = listOf("test"),
        onPhoneChanged = { _, _ -> Unit }, onPhoneLabelChanged = { _, _ -> Unit },
        onAddPhone = {},
        website = "test.com", onWebsiteChanged = {},
        onRegisterClicked = {},
        fullDate = "1390/9/1", onDateChanged = {}
    )
}