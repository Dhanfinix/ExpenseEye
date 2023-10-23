package com.dhandev.expenseeye.presentation.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dhandev.expenseeye.R
import com.dhandev.expenseeye.ui.theme.BlueSecondary
import com.dhandev.expenseeye.ui.theme.raleway
import com.dhandev.expenseeye.utils.DateUtil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerView(
    modifier: Modifier = Modifier,
    title: String,
    value: (String) -> Unit
) {
    val date = remember { mutableStateOf(DateUtil.millisToOnlyDate(System.currentTimeMillis())) }
    val showDialog = remember { mutableStateOf(false) }
    value.invoke(date.value)
    if (showDialog.value) {
        val datePickerState = rememberDatePickerState()
        val confirmEnabled = remember {
            derivedStateOf { datePickerState.selectedDateMillis != null }
        }
        DatePickerDialog(
            onDismissRequest = { showDialog.value = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        date.value = DateUtil.millisToOnlyDate(datePickerState.selectedDateMillis ?: System.currentTimeMillis())
                        showDialog.value = false
                    },
                    enabled = confirmEnabled.value
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDialog.value = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        ){DatePicker(datePickerState)}
    }

    Column {
        Text(
            modifier = Modifier.padding(bottom = 6.dp),
            text = title,
            style = raleway(fontSize = 14, weight = FontWeight.Normal)
        )
        TextField(
            value = stringResource(id = R.string.landing_start_period_date_content, date.value),
            onValueChange = { date.value = it },
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, BlueSecondary, MaterialTheme.shapes.small)
                .clickable {
                    showDialog.value = true
                },
            textStyle = raleway(fontSize = 16, weight = FontWeight.Medium),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            )
        )
    }
}