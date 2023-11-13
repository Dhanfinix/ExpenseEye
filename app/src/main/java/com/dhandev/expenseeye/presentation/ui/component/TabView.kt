package com.dhandev.expenseeye.presentation.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhandev.expenseeye.presentation.create.OutcomeScreen
import com.dhandev.expenseeye.ui.theme.ExpenseEyeTheme

@Composable
fun TabView() {
    val titles = listOf("Pengeluaran", "Pendapatan")
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Column {
        TabRow(selectedTabIndex = selectedTabIndex) {
            titles.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index }
                )
            }
        }
        when (selectedTabIndex) {
            0 -> OutcomeScreen(Modifier.padding(16.dp))
            1 -> Text("Content of Tab 2")
        }
    }
}

@Preview
@Composable
fun PreviewTab(){
    ExpenseEyeTheme {
        Surface {
            TabView()
        }
    }
}