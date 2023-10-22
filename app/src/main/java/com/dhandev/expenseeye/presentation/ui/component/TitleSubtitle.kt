package com.dhandev.expenseeye.presentation.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhandev.expenseeye.ui.theme.ExpenseEyeTheme
import com.dhandev.expenseeye.ui.theme.raleway

@Composable
fun TitleSubtitle(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String
){
    Column(
        modifier.fillMaxWidth().padding(10.dp)
    ) {
        Text(text = title, style = raleway(20, FontWeight.Bold))
        Text(text = subtitle, style = raleway(16, FontWeight.Normal))
    }
}

@Preview
@Composable
fun PreviewTitleSubtitle(){
    Surface {
        ExpenseEyeTheme {
            TitleSubtitle(title = "Selamat Datang", subtitle = "di ExpenseEye")
        }
    }
}