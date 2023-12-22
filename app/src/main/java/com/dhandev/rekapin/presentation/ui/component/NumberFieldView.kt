package com.dhandev.rekapin.presentation.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhandev.rekapin.ui.theme.BlueMain
import com.dhandev.rekapin.ui.theme.BlueSecondary
import com.dhandev.rekapin.ui.theme.RekapinTheme
import com.dhandev.rekapin.ui.theme.raleway
import com.dhandev.rekapin.utils.NumUtil.clearDot
import com.dhandev.rekapin.utils.NumUtil.clearThousandFormat
import com.dhandev.rekapin.utils.NumUtil.formatThousand

@Composable
fun NumberFieldView(
    modifier: Modifier = Modifier,
    title: String,
    setData: String = "",
    value: (String) -> Unit
){
    var text by remember {
        mutableStateOf(TextFieldValue(if (setData.isNotBlank()) {
            setData.toDouble().formatThousand()
        } else setData))
    }
    value.invoke(text.text)
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.padding(bottom = 6.dp),
            text = title,
            style = raleway(fontSize = 14, weight = FontWeight.Normal)
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { newInput ->
                val clearText = newInput.text.clearDot().clearThousandFormat()
                if (clearText.length <= 18 && clearText.matches(Regex("[0-9]*"))) {
                    val newValue = if (clearText.isNotBlank()) {
                        clearText.toDouble().formatThousand()
                    } else clearText

                    text = newInput.copy(
                        text = newValue,
                        selection = TextRange(newValue.length)
                    )
                }
            },
            placeholder = {
                Text(text = "100.000", color = Color.LightGray)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = BlueSecondary,
                cursorColor = BlueMain
            )
        )
    }
}

@Composable
@Preview
fun PreviewNumberField(){
    RekapinTheme {
        Surface {
            NumberFieldView(title = "Nominal", value = { "100000" })
        }
    }
}