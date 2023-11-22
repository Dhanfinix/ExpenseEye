package com.dhandev.rekapin.presentation.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhandev.rekapin.data.model.TransactionItemModel
import com.dhandev.rekapin.presentation.create.CreateViewModel
import com.dhandev.rekapin.presentation.create.IncomeScreen
import com.dhandev.rekapin.presentation.create.OutcomeScreen
import com.dhandev.rekapin.ui.theme.BlueMain
import com.dhandev.rekapin.ui.theme.RekapinTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun TabView(
    viewModel: CreateViewModel = koinViewModel(),
    trxData: TransactionItemModel?,
    onSuccess: () -> Unit
) {
    val titles = listOf("Pengeluaran", "Pendapatan")
    val selectedTabIndex = remember { mutableIntStateOf(0) }
    val firstOpened = remember { mutableStateOf(true) }

    if (trxData != null && firstOpened.value){
        selectedTabIndex.intValue = if(trxData.isExpense) 0 else 1
        firstOpened.value = false
    }

    Column {
        TabRow(
            selectedTabIndex = selectedTabIndex.intValue,
            indicator = {tabPositions->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex.intValue]),
                    color = BlueMain
                )
            }
            ) {
            titles.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title, color = BlueMain) },
                    selected = selectedTabIndex.intValue == index,
                    onClick = { selectedTabIndex.intValue = index }
                )
            }
        }
        when (selectedTabIndex.intValue) {
            0 -> OutcomeScreen(Modifier.padding(16.dp), onSuccess = { onSuccess.invoke() }, trxData = trxData)
            1 -> IncomeScreen(Modifier.padding(16.dp), onSuccess = { onSuccess.invoke() })
        }
    }
}

@Preview
@Composable
fun PreviewTab(){
    RekapinTheme {
        Surface {
            TabView(trxData = null) {}
        }
    }
}