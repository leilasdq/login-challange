package com.example.jecpackchallange.login

import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jecpackchallange.R
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
    val uiState = viewModel.loginState

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
            label = "email", itemValue = emailList[0], itemLabel = emailLabelList[0],
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
            label = "phone", itemValue = phoneList[0], itemLabel = phoneLabelList[0],
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
            icon = Icons.Outlined.DateRange
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