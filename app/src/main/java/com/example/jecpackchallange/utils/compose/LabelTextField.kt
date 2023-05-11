package com.example.jecpackchallange.utils.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LabelTextField(
    label: String,
    itemValue: String,
    itemLabel: String,
    onItemValueChanged: (String) -> Unit,
    onItemLabelChanged: (String) -> Unit,
    isPrimary: Boolean,
    onAddItem: () -> Unit,
    onDeleteItem: () -> Unit,
    onSelectAsPrimaryClicked: (Boolean) -> Unit,
    icon: ImageVector? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    focusManager: FocusManager,
    showDeleteIcon: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(1f)) {
                ChallengeTextField(
                    label = label,
                    value = itemValue,
                    onValueChange = {
                        onItemValueChanged.invoke(it)
                    },
                    icon = icon,
                    keyboardOptions = keyboardOptions,
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(
                                FocusDirection.Down
                            )
                        }),
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            if (showDeleteIcon) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            onDeleteItem()
                        }
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(1f)) {
                ChallengeTextField(
                    label = "label",
                    value = itemLabel,
                    onValueChange = {
                        onItemLabelChanged.invoke(it)
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(
                                FocusDirection.Down
                            )
                        }),
                )
            }
            ChallengeCheckBox(
                value = "primary",
                onCheckBoxSelectionClicked = { onSelectAsPrimaryClicked.invoke(it) },
                isSelected = isPrimary
            )
            Spacer(modifier = Modifier.size(8.dp))
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        onAddItem()
                    }
            )
        }
    }

}


@Preview
@Composable
private fun PrevForItems() {
    LabelTextField(
        label = "email", itemValue = "email@gmail", itemLabel = "work",
        onItemLabelChanged = {}, onItemValueChanged = {},
        isPrimary = true, onSelectAsPrimaryClicked = {},
        onAddItem = {}, onDeleteItem = {},
        focusManager = LocalFocusManager.current,
        showDeleteIcon = true
    )
}