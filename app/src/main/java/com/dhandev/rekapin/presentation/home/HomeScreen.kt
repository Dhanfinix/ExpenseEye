package com.dhandev.rekapin.presentation.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dhandev.rekapin.R
import com.dhandev.rekapin.data.model.FilterModel
import com.dhandev.rekapin.data.model.TransactionGroupModel
import com.dhandev.rekapin.data.model.TransactionItemModel
import com.dhandev.rekapin.navigation.NavigationDestination
import com.dhandev.rekapin.presentation.landing.MainViewModel
import com.dhandev.rekapin.presentation.ui.component.homeCard.HomeCardView
import com.dhandev.rekapin.presentation.ui.component.ChipGroup
import com.dhandev.rekapin.presentation.ui.component.DetailBottomSheet
import com.dhandev.rekapin.presentation.ui.component.EmptyTransaction
import com.dhandev.rekapin.presentation.ui.component.TransactionGroup
import com.dhandev.rekapin.presentation.ui.component.homeCard.HomeCardState
import com.dhandev.rekapin.ui.theme.BlueMain
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = koinViewModel(),
    paddingValues: PaddingValues,
    navigateToCreate: (String?) -> Unit,
) {
    getData(viewModel)

    val lifecycleOwner = LocalLifecycleOwner.current
    val filter = remember { mutableLongStateOf(DateUtil.fromDateInMillisToday) }       //today
    val groupedData = remember { mutableStateOf<List<TransactionGroupModel>?>(emptyList()) }
    val selectedFilter = remember { mutableIntStateOf(0) }
    val balance = remember { mutableDoubleStateOf(0.0) }
    val balanceThisMonth = remember { mutableDoubleStateOf(0.0) }
    val budget = remember { mutableDoubleStateOf(0.0) }
    val targetAchieved = remember { mutableDoubleStateOf(0.0) }
    val showBalance = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    val scope = rememberCoroutineScope()
    var selectedDetail by remember { mutableStateOf<TransactionItemModel?>(null) }

    viewModel.filter.observe(lifecycleOwner){
        filter.longValue = it
    }
    viewModel.filterIndex.observe(lifecycleOwner){
        selectedFilter.intValue = it
    }
    viewModel.showBalance.observe(lifecycleOwner){
        showBalance.value = it
    }
    viewModel.balance.observe(lifecycleOwner){
        balance.doubleValue = it
    }
    viewModel.expense.observe(lifecycleOwner) {
        balanceThisMonth.doubleValue = viewModel.budget.doubleValue - it
    }
    viewModel.expense.observe(lifecycleOwner) {
        val result = 1.0 - it.div(viewModel.budget.doubleValue)
        budget.doubleValue = String.format("%.2f", result).toDouble()
    }
    val fractionOfTarget = balance.doubleValue.div(viewModel.target.doubleValue)
    targetAchieved.doubleValue = if (fractionOfTarget <= 1.0){
        String.format("%.2f", fractionOfTarget.let { if (it == 1.0) 0.0 else it}).toDouble()
    } else {
        1.0
    }

    val gradient = Brush.radialGradient(
        0.0f to BlueMain,
        1.0f to MaterialTheme.colorScheme.surface,
        radius = 700.0f,
        center = Offset(1000f, 0f)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradient)
            .padding(paddingValues)
    ) {
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
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(text = getGreetings(), style = raleway(20, FontWeight.Bold))
                    Text(text = viewModel.username.value, style = raleway(16, FontWeight.Normal))
                }
            }
            item {
                HomeCardView(
                    modifier = modifier.fillMaxWidth(),
                    state = HomeCardState(
                        balance,
                        balanceThisMonth,
                        budget,
                        targetAchieved,
                        viewModel.target,
                        showBalance
                    ) {
                        viewModel.saveShowBalance(it)
                    }
                )
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
                    viewModel.saveFilter(
                        FilterModel(
                            index = it,
                            filterInMillis = when (it) {
                                0 -> DateUtil.fromDateInMillisToday
                                1 -> DateUtil.fromDateInMillis7Days
                                2 -> DateUtil.fromDateInMillis30Days
                                else -> DateUtil.fromReportPeriodDate(viewModel.reportPeriod.intValue)
                            }
                        )
                    )
                }
            }

            if (groupedData.value.isNullOrEmpty()) {
                item {
                    EmptyTransaction(Modifier.padding(top = 20.dp))
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
                                sheetState.show()
                            }
                        }
                    )
                }
            }
        }

        viewModel.getTheme()
        ExtendedFloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            text = { Text(text = "Transaksi") },
            onClick = {
                selectedDetail = null
                navigateToCreate.invoke(null)
            },
            icon = { Icon(Icons.Filled.Add, "") },
            containerColor = BlueSecondary,
            contentColor = if (viewModel.isDark.value!!) MaterialTheme.colorScheme.inverseOnSurface else MaterialTheme.colorScheme.onSurface
        )

        AnimUtil.AnimatedVisibility(visible = scaffoldState.bottomSheetState.isVisible) {
            Surface(
                color = Color.Black.copy(alpha = 0.6f),
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        interactionSource = remember {
                            MutableInteractionSource()
                        },
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
                    scope.launch {
                        val trxData = Gson().toJson(selectedDetail)
                        sheetState.hide()
                        navigateToCreate(trxData)
                        selectedDetail = null
                    }
                }
            )
        }

        BackHandler(enabled = scaffoldState.bottomSheetState.isVisible) {
            scope.launch {
                sheetState.hide()
                selectedDetail = null
            }
        }
    }
}

private fun getData(viewModel: MainViewModel) {
    viewModel.getFilter()
    viewModel.getIncomeExpense()
    viewModel.getExpense()
    viewModel.getShowBalance()
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