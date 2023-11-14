package com.dhandev.expenseeye.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.dhandev.expenseeye.R
import com.dhandev.expenseeye.ui.theme.BlueMain
import com.dhandev.expenseeye.ui.theme.BlueSecondary
import com.dhandev.expenseeye.ui.theme.raleway
import com.dhandev.expenseeye.utils.DateUtil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerView(
    modifier: Modifier = Modifier,
    title: String,
    value: (Long) -> Unit
) {
    val dateInMillis = remember { mutableLongStateOf(System.currentTimeMillis()) }
    val date = remember { mutableStateOf(DateUtil.millisToDate(dateInMillis.longValue)) }
    val showDialog = remember { mutableStateOf(false) }
    val borderColor = if (showDialog.value) BlueSecondary else Color.Gray
    value.invoke(dateInMillis.longValue)

    if (showDialog.value) {
        val datePickerState = rememberDatePickerState()
        val confirmEnabled = remember {
            derivedStateOf { datePickerState.selectedDateMillis != null }
        }
        DatePickerDialog(
            modifier = modifier,
            onDismissRequest = { showDialog.value = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        dateInMillis.longValue = datePickerState.selectedDateMillis ?: System.currentTimeMillis()
                        date.value = DateUtil.millisToDate(dateInMillis.longValue)
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
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    showDialog.value = true
                },
            value =  date.value,
            onValueChange = {},
            enabled = false,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = borderColor,
                disabledTextColor = Color.Black
            ),
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.ic_dates),
                    contentDescription = null
                )
            }
        )
    }
}