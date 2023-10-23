package com.dhandev.expenseeye.presentation.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhandev.expenseeye.ui.theme.BlueSecondary
import com.dhandev.expenseeye.ui.theme.ExpenseEyeTheme
import com.dhandev.expenseeye.ui.theme.raleway

@Composable
fun TextFieldView(
    modifier : Modifier = Modifier,
    title: String,
    keyboardType: KeyboardType,
    value: (String) -> Unit
) {
    var data by remember { mutableStateOf("") }
    value.invoke(data)
    Column(modifier) {
        Text(
            modifier = Modifier.padding(bottom = 6.dp),
            text = title,
            style = raleway(fontSize = 14, weight = FontWeight.Normal))
        TextField(
            value = data,
            onValueChange = {newText -> data = newText },
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, BlueSecondary, MaterialTheme.shapes.small),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            maxLines = 1,
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

@Preview
@Composable
fun PreviewTextField(){
    var typed by remember { mutableStateOf("") }

    ExpenseEyeTheme {
        Surface {
            Column {
                TextFieldView(
                    title = "Nama kamu",
                    modifier = Modifier.padding(16.dp),
                    keyboardType = KeyboardType.Text,
                    value = {typed = it}
                )
                TextFieldView(
                    title = "Nama kamu",
                    modifier = Modifier.padding(16.dp),
                    keyboardType = KeyboardType.Text,
                    value = {typed = it}
                )
            }
        }
    }
}