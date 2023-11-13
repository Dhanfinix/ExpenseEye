package com.dhandev.expenseeye.presentation.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle

@Composable
fun NonFocusableOutlinedTextField(
    value: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val isClicked = remember { mutableStateOf(false) }
    val borderColor = if (isClicked.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = {
                isClicked.value = !isClicked.value
                onClick()
            })
    ) {
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = value,
            onValueChange = {},
            readOnly = true,
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
            cursorBrush = SolidColor(Color.Transparent),
            decorationBox = {
                OutlinedTextField(
                    value = "Select Category",
                    onValueChange = {},
                    readOnly = true,
                    singleLine = true,
                    enabled = false,
                    interactionSource = remember { MutableInteractionSource() },
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = Color.LightGray,
                        cursorColor = MaterialTheme.colorScheme.onSurface,
                        errorCursorColor = MaterialTheme.colorScheme.error,
                        focusedBorderColor = borderColor,
                        unfocusedBorderColor = borderColor,
                        disabledBorderColor = borderColor,
                        errorBorderColor = MaterialTheme.colorScheme.error,
                        disabledLeadingIconColor = MaterialTheme.colorScheme.onSurface,
                        errorLeadingIconColor = MaterialTheme.colorScheme.error,
                        disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface,
                        errorTrailingIconColor = MaterialTheme.colorScheme.error
                    )
                )
            }
        )
    }
}
