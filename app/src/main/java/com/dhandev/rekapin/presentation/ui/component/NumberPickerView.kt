package com.dhandev.rekapin.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.chargemap.compose.numberpicker.NumberPicker
import com.dhandev.rekapin.R
import com.dhandev.rekapin.ui.theme.BlueMain
import com.dhandev.rekapin.ui.theme.BlueSecondary
import com.dhandev.rekapin.ui.theme.raleway

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberPickerView(
    modifier: Modifier = Modifier,
    title: String,
    setData: Int = 14,
    value: (Int) -> Unit
) {
    val date = remember { mutableIntStateOf(setData) }
    val showDialog = remember { mutableStateOf(false) }
    val borderColor = if (showDialog.value) BlueSecondary else Color.Gray
    value.invoke(date.intValue)

    if (showDialog.value) {
        var pickerValue by remember { mutableIntStateOf(14) }

        Dialog(onDismissRequest = { showDialog.value = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = modifier.padding(bottom = 12.dp),
                        text = stringResource(id = R.string.landing_start_period_date),
                        style = raleway(fontSize = 16, weight = FontWeight.Bold)
                    )
                    NumberPicker(
                        modifier = Modifier.fillMaxWidth(),
                        dividersColor = BlueMain,
                        value = pickerValue,
                        range = 1..28,
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
                        onValueChange = {
                            pickerValue = it
                        },
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        TextButton(
                            onClick = { showDialog.value = false },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("Cancel")
                        }
                        TextButton(
                            onClick = {
                                date.intValue = pickerValue
                                showDialog.value = false
                            },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("Confirm")
                        }
                    }
                }
            }
        }

    }

    Column(modifier) {
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
            value = stringResource(id = R.string.landing_start_period_date_content, date.value),
            onValueChange = {},
            enabled = false,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = borderColor,
                disabledTextColor = MaterialTheme.colorScheme.onSurface
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