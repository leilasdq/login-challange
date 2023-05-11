package com.example.jecpackchallange.utils.compose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ChallengeTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    icon: ImageVector? = null,
    readOnly: Boolean = false,
    enable: Boolean = true,
    modifier: Modifier = Modifier,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    isError: Boolean = false,
    errorMessage: String? = null,
    maxLength: Int = Int.MAX_VALUE
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null)
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        else
            Box(modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.size(8.dp))
        Column {
            OutlinedTextField(
                value = value,
                onValueChange = {
                    if (it.length <= maxLength) onValueChange(it)
                },
                label = { Text(text = label) },
                readOnly = readOnly,
                enabled = enable,
                modifier = modifier
                    .heightIn(48.dp),
                trailingIcon = trailingIcon,
                keyboardActions = keyboardActions,
                keyboardOptions = keyboardOptions,
                isError = isError,
                maxLines = maxLength
            )
            if (errorMessage != null) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp, top = 0.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun ChallengeTextFieldPrevWithNoIcon() {
    ChallengeTextField(
        "phone",
        "1234", {},

        )
}
@Preview
@Composable
private fun ChallengeTextFieldPrevWithIcon() {
    ChallengeTextField(
        "phone",
        "1234", {},
        Icons.Default.Add
    )
}