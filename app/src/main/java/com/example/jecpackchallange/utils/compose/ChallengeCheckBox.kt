package com.example.jecpackchallange.utils.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@Composable
fun ChallengeCheckBox(
    value: String,
    isSelected: Boolean,
    onCheckBoxSelectionClicked: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Checkbox(
            checked = isSelected,
            onCheckedChange = {
                onCheckBoxSelectionClicked.invoke(it)
            },
        )
        Text(text = value)
    }
}