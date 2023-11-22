package com.dhandev.rekapin.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.dhandev.rekapin.data.model.TransactionItemModel
import com.dhandev.rekapin.navigation.NavigationDestination
import com.dhandev.rekapin.presentation.landing.MainViewModel
import com.dhandev.rekapin.presentation.ui.component.BalanceCardView
import com.dhandev.rekapin.presentation.ui.component.ChipGroup
import com.dhandev.rekapin.presentation.ui.component.DetailBottomSheet
import com.dhandev.rekapin.presentation.ui.component.TitleSubtitle
import com.dhandev.rekapin.presentation.ui.component.TransactionGroup
import com.dhandev.rekapin.ui.theme.BlueSecondary
import com.dhandev.rekapin.ui.theme.raleway
import com.dhandev.rekapin.utils.AnimUtil
import com.dhandev.rekapin.utils.DateUtil
import com.google.gson.Gson
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.util.Calendar

object HomeDestination : NavigationDestination {
    override val route: String = "home"
    override val titleRes: Int = R.string.home
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = koinViewModel(),
    navigateToCreate: (String?) -> Unit,
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
    val showBalance by remember { mutableStateOf(viewModel.showBalance) }

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

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    val scope = rememberCoroutineScope()
//    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedDetail by remember { mutableStateOf<TransactionItemModel?>(null) }

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
                TitleSubtitle(title =  getGreetings(), subtitle = viewModel.username.value)
            }
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
                        data = groupedData.value!![index],
                        itemClicked = {
                            selectedDetail = it
                            scope.launch {
                                if (sheetState.isVisible){
                                    sheetState.hide()
                                } else {
                                    sheetState.show()
                                }
                            }
                        }
                    )
                }
            }
        }

        ExtendedFloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            text = { Text(text = "Transaksi") },
            onClick = { navigateToCreate.invoke(null) },
            icon = { Icon(Icons.Filled.Add, "") },
            containerColor = BlueSecondary
        )

        AnimUtil.AnimatedVisibility(visible = sheetState.isVisible) {
            Surface(
                color = Color.Black.copy(alpha = 0.6f),
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) {
                        scope.launch {
                            sheetState.hide()
                            selectedDetail = null
                        }
                    }
            ){}
        }

        if (selectedDetail != null){
            DetailBottomSheet(
                scaffoldState = scaffoldState,
                data = selectedDetail!!,
                onDelete = {
                    scope.launch {
                        viewModel.deleteItem(selectedDetail!!)
                        sheetState.hide()
                        selectedDetail = null
                    }
                },
                onUpdate = {
                    val trxData = Gson().toJson(selectedDetail)
                    navigateToCreate(trxData)
                }
            )
        }
    }
}

private fun getGreetings() : String {
    val currentTime = Calendar.getInstance().timeInMillis
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = currentTime
    return when (calendar.get(Calendar.HOUR_OF_DAY)) {
        in 6..11 -> "Good morning!"
        in 12..13 -> "It's noon!"
        in 14..17 -> "Good afternoon!"
        in 18..20 -> "Good evening!"
        else -> "Good night!"
    }
}