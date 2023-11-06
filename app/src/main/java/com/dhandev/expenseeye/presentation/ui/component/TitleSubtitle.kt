package com.dhandev.expenseeye.presentation.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhandev.expenseeye.ui.theme.ExpenseEyeTheme
import com.dhandev.expenseeye.ui.theme.raleway

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleSubtitle(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    scrollBehavior: TopAppBarScrollBehavior  = TopAppBarDefaults.pinnedScrollBehavior()// Add this line
){
    TopAppBar(  // Wrap your Column in a TopAppBar
        modifier = modifier,
        scrollBehavior = scrollBehavior,  // Add this line
        title = {
            Column(
                modifier
                    .fillMaxWidth()
            ) {
                Text(text = title, style = raleway(20, FontWeight.Bold))
                Text(text = subtitle, style = raleway(16, FontWeight.Normal))
            }
        }
    )
}

@Preview
@Composable
fun PreviewTitleSubtitle(){
    Surface {
//        ExpenseEyeTheme {
//            TitleSubtitle(title = "Selamat Datang", subtitle = "di ExpenseEye")
//        }
    }
}