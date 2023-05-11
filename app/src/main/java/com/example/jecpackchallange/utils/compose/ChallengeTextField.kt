package com.example.jecpackchallange.utils.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jecpackchallange.R

@Composable
fun ChallengeTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    icon: ImageVector? = null,
    readOnly: Boolean = false,
    enable: Boolean = true,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
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
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = label) },
            readOnly = readOnly,
            enabled = enable,
            modifier = modifier
                .fillMaxWidth()
        )
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