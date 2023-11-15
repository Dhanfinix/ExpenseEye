package com.dhandev.expenseeye.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dhandev.expenseeye.R
import com.dhandev.expenseeye.data.model.TransactionGroupModel
import com.dhandev.expenseeye.navigation.NavigationDestination
import com.dhandev.expenseeye.presentation.landing.MainViewModel
import com.dhandev.expenseeye.presentation.ui.component.BalanceCardView
import com.dhandev.expenseeye.presentation.ui.component.ChipGroup
import com.dhandev.expenseeye.presentation.ui.component.TransactionGroup
import com.dhandev.expenseeye.ui.theme.BlueSecondary
import com.dhandev.expenseeye.ui.theme.raleway
import com.dhandev.expenseeye.utils.DateUtil
import org.koin.androidx.compose.koinViewModel

object HomeDestination : NavigationDestination {
    override val route: String = "home"
    override val titleRes: Int = R.string.home
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = koinViewModel(),
    navigateToCreate: () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val gradient = Brush.linearGradient(
        colors = listOf(BlueSecondary, MaterialTheme.colorScheme.background),
        start = Offset(800f, 0f),
        end = Offset(0f, 400f)
    )
    val filter = remember { mutableIntStateOf(0) }       //today
//    val groupedData = remember { mutableStateOf(listOf(TransactionGroupModel("", listDummyItemData))) }
    val groupedData = remember { mutableStateOf<List<TransactionGroupModel>?>(null) }

    Box(modifier = Modifier.fillMaxSize()){
        viewModel.getAll().observe(lifecycleOwner){data->
            groupedData.value = data
                .groupBy {
                    DateUtil.millisToDateForGroup(it.dateInMillis)
                }.map {
                    TransactionGroupModel(it.key, it.value)
                }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            userScrollEnabled = true
        ) {
            item {
                BalanceCardView(modifier.padding(vertical = 8.dp, horizontal = 16.dp))
            }
            item {
                Text(
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
                    text = stringResource(id = R.string.expense_list),
                    style = raleway(
                        fontSize = 16,
                        weight = FontWeight.Bold
                    )
                )
            }
            item {
                ChipGroup(
                    modifier = Modifier.padding(),
                    items = listOf("Hari ini", "7 hari", "2 minggu", "1 bulan", "Periode pencatatan")
                ) {
                    //TODO: Filter recent transaction
                    filter.intValue = it
                }
            }

            groupedData.value?.let {
                items(it.size) { index ->
                    TransactionGroup(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = if (index == it.size-1) 50.dp else 0.dp),
                        data = it[index]
                    )
                }
            }
//            val groupedData = listDummyItemData
//                .filter {
//                    when (filter.intValue) {
//                        0 -> it.dateInMillis >= System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1) //today
//                        1 -> it.dateInMillis >= System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7) //7 days
//                        2 -> it.dateInMillis >= System.currentTimeMillis() - TimeUnit.DAYS.toMillis(14) //2 weeks
//                        3 -> it.dateInMillis >= System.currentTimeMillis() - TimeUnit.DAYS.toMillis(30) //1 month
//                        else -> false
//                    }
//                }
//                .groupBy {
//                    DateUtil.millisToDateForGroup(it.dateInMillis)
//                }.map {
//                    TransactionGroupModel(it.key, it.value)
//                }.sortedBy {
//                    it.date
//                }
//            items(groupedData.count()) {
//                TransactionGroup(
//                    modifier = Modifier.padding(horizontal = 16.dp),
//                    data = groupedData[it]
//                )
//            }

        }

        ExtendedFloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            text = { Text(text = "Transaksi") },
            onClick = {navigateToCreate.invoke()},
            icon = { Icon(Icons.Filled.Add, "") },
            containerColor = BlueSecondary
        )
    }
}