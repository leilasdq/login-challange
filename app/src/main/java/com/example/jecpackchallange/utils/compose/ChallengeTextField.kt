package com.example.jecpackchallange.utils.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jecpackchallange.R

@Composable
fun ChallengeTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    icon: Painter? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null)
            Icon(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        else
            Box(modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.size(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = label) }
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
        painterResource(id = R.drawable.ic_launcher_background)
    )
}