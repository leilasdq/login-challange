package com.example.jecpackchallange.register

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jecpackchallange.R
import com.example.jecpackchallange.utils.compose.ChallengeTextField
import com.example.jecpackchallange.utils.compose.LabelTextField
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun LoginScreen(
    viewModel: RegisterViewModel
) {
    val uiState by viewModel.registerState.collectAsState()
    val context = LocalContext.current
    val successfulMsg = stringResource(id = R.string.registered_successfully)

    if (uiState.success) {
        LaunchedEffect(key1 = viewModel) {
            Toast
                .makeText(context, successfulMsg, Toast.LENGTH_SHORT)
                .show()
            viewModel.onTriggerEvent(RegisterEvent.ResetSuccessState)
        }
    }

    LoginScreenContent(
        name = uiState.name,
        onNameChanged = {
            viewModel.onTriggerEvent(RegisterEvent.OnNameChanged(it))
        },
        nameError = uiState.nameError,
        emailList = uiState.emailItemList,
        onEmailChanged = { index, email ->
            viewModel.onTriggerEvent(RegisterEvent.OnEmailChanged(index, email))
        },
        onEmailLabelChanged = { index, label ->
            viewModel.onTriggerEvent(RegisterEvent.OnEmailLabelChanged(index, label))
        },
        onAddEmail = {
            viewModel.onTriggerEvent(RegisterEvent.OnAddEmail)
        },
        onDeleteEmail = {
            viewModel.onTriggerEvent(RegisterEvent.OnEmailDeleted(it))
        },
        onEmailPrimaryStateChanged = { index, value ->
            viewModel.onTriggerEvent(RegisterEvent.OnEmailPrimaryStateChanged(index, value))
        },
        phoneList = uiState.phoneItemList,
        onPhoneChanged = { index, phone ->
            viewModel.onTriggerEvent(RegisterEvent.OnPhoneChanged(index, phone))
        },
        onPhoneLabelChanged = { index, label ->
            viewModel.onTriggerEvent(RegisterEvent.OnPhoneLabelChanged(index, label))
        },
        onAddPhone = { viewModel.onTriggerEvent(RegisterEvent.OnAddPhone) },
        onDeletePhone = {
            viewModel.onTriggerEvent(RegisterEvent.OnPhoneDeleted(it))
        },
        onPhonePrimaryStateChanged = { index, value ->
            viewModel.onTriggerEvent(RegisterEvent.OnPhonePrimaryStateChanged(index, value))
        },
        website = uiState.site,
        onWebsiteChanged = {
            viewModel.onTriggerEvent(RegisterEvent.OnWebsiteChanged(it))
        },
        siteError = uiState.siteError,
        onRegisterClicked = {
            viewModel.onTriggerEvent(RegisterEvent.OnRegisterClicked)
        },
        fullDate = uiState.birthday,
        onDateChanged = {
            viewModel.onTriggerEvent(RegisterEvent.OnBirthDateChanged(it))
        },
        birthdayError = uiState.birthdayError
    )
}

@Composable
private fun LoginScreenContent(
    name: String,
    onNameChanged: (String) -> Unit,
    nameError: String?,
    emailList: List<RegisterItems>,
    onEmailChanged: (Int, String) -> Unit,
    onEmailLabelChanged: (Int, String) -> Unit,
    onAddEmail: () -> Unit,
    onDeleteEmail: (Int) -> Unit,
    onEmailPrimaryStateChanged: (Int, Boolean) -> Unit,
    phoneList: List<RegisterItems>,
    onPhoneChanged: (Int, String) -> Unit,
    onPhoneLabelChanged: (Int, String) -> Unit,
    onAddPhone: () -> Unit,
    onDeletePhone: (Int) -> Unit,
    onPhonePrimaryStateChanged: (Int, Boolean) -> Unit,
    website: String,
    onWebsiteChanged: (String) -> Unit,
    siteError: String?,
    onRegisterClicked: () -> Unit,
    fullDate: String,
    onDateChanged: (String) -> Unit,
    birthdayError: String?
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
            positiveButton(text = stringResource(id = android.R.string.ok)) {
                onDateChanged(formattedDate)
            }
            negativeButton(text = stringResource(id = android.R.string.cancel))
        }
    ) {
        datepicker(
            initialDate = LocalDate.now(),
            title = stringResource(id = R.string.pick_date),
            allowedDateValidator = {
                it <= LocalDate.now()
                it.year <= LocalDate.now().year
            }
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
                text = stringResource(id = R.string.register),
                style = MaterialTheme.typography.h4
            )
            Button(
                shape = RoundedCornerShape(50),
                onClick = { onRegisterClicked() },
            ) {
                Text(text = stringResource(id = R.string.submit))
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
        ChallengeTextField(
            label = stringResource(id = R.string.name),
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
            isError = nameError != null,
            errorMessage = nameError
        )
        emailList.forEachIndexed { index, loginItem ->
            LabelTextField(
                label = stringResource(id = R.string.email),
                itemValue = loginItem.value,
                itemLabel = loginItem.label,
                onItemValueChanged = {
                    onEmailChanged(index, it)
                },
                onItemLabelChanged = {
                    onEmailLabelChanged.invoke(index, it)
                },
                isPrimary = loginItem.isPrimary,
                onAddItem = onAddEmail,
                onDeleteItem = {
                    onDeleteEmail(index)
                },
                onPrimaryStateChanged = {
                    onEmailPrimaryStateChanged(index, it)
                },
                icon = Icons.Outlined.Email,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                ),
                focusManager = focusManager,
                showDeleteIcon = loginItem.isPrimary.not(),
                isError = loginItem.error != null,
                errorMessage = loginItem.error
            )
        }
        phoneList.forEachIndexed { index, loginItem ->
            LabelTextField(
                label = stringResource(id = R.string.phone),
                itemValue = loginItem.value,
                itemLabel = loginItem.label,
                onItemValueChanged = {
                    onPhoneChanged(index, it)
                },
                onItemLabelChanged = {
                    onPhoneLabelChanged.invoke(index, it)
                },
                isPrimary = loginItem.isPrimary,
                onAddItem = onAddPhone,
                onDeleteItem = {
                    onDeletePhone(index)
                },
                onPrimaryStateChanged = {
                    onPhonePrimaryStateChanged(index, it)
                },
                icon = Icons.Outlined.Phone,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Phone
                ),
                focusManager = focusManager,
                showDeleteIcon = loginItem.isPrimary.not(),
                isError = loginItem.error != null,
                errorMessage = loginItem.error,
                maxLength = 11
            )
        }
        ChallengeTextField(
            label = stringResource(id = R.string.website),
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
            ),
            isError = siteError != null,
            errorMessage = siteError
        )
        ChallengeTextField(
            label = stringResource(id = R.string.birthday),
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
            },
            isError = birthdayError != null,
            errorMessage = birthdayError
        )
    }
}

@Preview
@Composable
private fun Prev() {
    LoginScreenContent(
        name = "Leila", onNameChanged = {},
        emailList = listOf(RegisterItems("test@gmail.com", "test", false)),
        onEmailChanged = { _, _ -> Unit }, onEmailLabelChanged = { _, _ -> Unit },
        onAddEmail = {},
        phoneList = listOf(RegisterItems("111", "testtt", true)),
        onPhoneChanged = { _, _ -> Unit }, onPhoneLabelChanged = { _, _ -> Unit },
        onAddPhone = {},
        website = "test.com", onWebsiteChanged = {},
        onRegisterClicked = {},
        fullDate = "1390/9/1", onDateChanged = {},
        onDeleteEmail = {}, onDeletePhone = {},
        onEmailPrimaryStateChanged = {_, _ -> Unit},
        onPhonePrimaryStateChanged = {_, _ -> Unit},
        nameError = null, siteError = "not ok",
        birthdayError = "hmmm"
    )
}