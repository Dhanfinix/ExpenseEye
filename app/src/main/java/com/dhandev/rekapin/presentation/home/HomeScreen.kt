package com.dhandev.rekapin.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.dhandev.rekapin.R
import com.dhandev.rekapin.data.model.TransactionGroupModel
import com.dhandev.rekapin.navigation.NavigationDestination
import com.dhandev.rekapin.presentation.landing.MainViewModel
import com.dhandev.rekapin.presentation.ui.component.BalanceCardView
import com.dhandev.rekapin.presentation.ui.component.ChipGroup
import com.dhandev.rekapin.presentation.ui.component.TransactionGroup
import com.dhandev.rekapin.ui.theme.BlueSecondary
import com.dhandev.rekapin.ui.theme.raleway
import com.dhandev.rekapin.utils.DateUtil
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
    val filter = remember { mutableLongStateOf(DateUtil.currentDate()) }       //today
    val groupedData = remember { mutableStateOf<List<TransactionGroupModel>?>(emptyList()) }
    val selectedFilter = remember { mutableIntStateOf(0) }
    val composition = rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.anim_empty))
    val progress by animateLottieCompositionAsState(
        composition = composition.value,
        iterations = LottieConstants.IterateForever
    )
    viewModel.getIncomeExpense()
    viewModel.getExpense()
    val balance = remember { mutableDoubleStateOf(0.0) }
    val balanceThisMonth = remember { mutableDoubleStateOf(0.0) }
    val budget = remember { mutableFloatStateOf(0f) }

    viewModel.getShowBalance()
    var showBalance by remember { mutableStateOf(viewModel.showBalance) }

    viewModel.balance.observe(lifecycleOwner) {
        balance.doubleValue = it
    }
    viewModel.expense.observe(lifecycleOwner) {
        balanceThisMonth.doubleValue = viewModel.budget.doubleValue - it
    }
    viewModel.expense.observe(lifecycleOwner) {
        val result = 1f - it.div(viewModel.budget.doubleValue).toFloat()
        budget.floatValue = String.format("%.2f", result).toFloat()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        viewModel.getAll(filter.longValue).observe(lifecycleOwner) { data ->
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
                BalanceCardView(
                    modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                    balanceThisMonth.doubleValue,
                    budget.floatValue,
                    showBalance.value
                ) {
                    showBalance.value = it
                    viewModel.saveShowBalance(it)
                }
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
                    items = listOf("Hari ini", "7 hari", "30 hari", "Periode pencatatan"),
                    selectedItem = selectedFilter
                ) {
                    selectedFilter.intValue = it
                    filter.longValue = when (it) {
                        0 -> DateUtil.fromDateInMillisToday()
                        1 -> DateUtil.fromDateInMillis7Days
                        2 -> DateUtil.fromDateInMillis30Days
                        else -> DateUtil.fromReportPeriodDate(viewModel.reportPeriod.intValue)
                    }
                }
            }

            if (groupedData.value.isNullOrEmpty()) {
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LottieAnimation(
                            composition = composition.value,
                            progress = { progress },
                            modifier = Modifier
                                .height(200.dp)
                                .fillMaxWidth()
                        )
                        Text(
                            text = "Belum ada transaksi",
                            style = raleway(
                                fontSize = 16,
                                weight = FontWeight.Normal
                            )
                        )
                    }
                }
            } else {
                items(groupedData.value!!.size) { index ->
                    TransactionGroup(
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = if (index == groupedData.value!!.size - 1) 50.dp else 0.dp
                        ),
                        data = groupedData.value!![index]
                    )
                }
            }
        }

        ExtendedFloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            text = { Text(text = "Transaksi") },
            onClick = { navigateToCreate.invoke() },
            icon = { Icon(Icons.Filled.Add, "") },
            containerColor = BlueSecondary
        )
    }
}