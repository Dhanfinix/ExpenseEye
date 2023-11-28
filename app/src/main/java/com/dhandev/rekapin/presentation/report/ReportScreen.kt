package com.dhandev.rekapin.presentation.report

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dhandev.rekapin.R
import com.dhandev.rekapin.data.model.CategoryGroupModel
import com.dhandev.rekapin.data.model.TransactionItemModel
import com.dhandev.rekapin.navigation.NavigationDestination
import com.dhandev.rekapin.presentation.landing.MainViewModel
import com.dhandev.rekapin.presentation.ui.component.CategoryItemView
import com.dhandev.rekapin.presentation.ui.component.Chart
import com.dhandev.rekapin.presentation.ui.component.ChipGroup
import com.dhandev.rekapin.presentation.ui.component.EmptyTransaction
import com.dhandev.rekapin.ui.theme.BlueSecondary
import com.dhandev.rekapin.ui.theme.raleway
import com.dhandev.rekapin.utils.DateUtil
import org.koin.androidx.compose.koinViewModel

object ReportDestination : NavigationDestination{
    override val route: String = "report"
    override val titleRes: Int = R.string.report
}
@Composable
fun ReportScreen(
    modifier : Modifier = Modifier,
    paddingValues: PaddingValues,
    viewModel: MainViewModel = koinViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val itemsData = remember { mutableStateOf<List<TransactionItemModel>?>(emptyList()) }
    val filter = remember { mutableLongStateOf(DateUtil.currentDate()) }       //today
    val selectedFilter = remember { mutableIntStateOf(0) }
    val groupedData = remember { mutableStateOf<List<CategoryGroupModel>?>(emptyList()) }

    viewModel.getAll(filter.longValue).observe(lifecycleOwner) { data ->
        itemsData.value = data
        groupedData.value =data
            .groupBy {
                it.category
            }.map {item->
                val total = item.value.sumOf {  if (it.isExpense) -it.total else it.total }
                val proportion = total * 100 / data.sumOf { it.total }
                CategoryGroupModel(item.key, total, proportion.toFloat())
            }.sortedByDescending {
                it.proportion
            }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(text = "Here is your money report", style = raleway(20, FontWeight.Bold))
        }
        ChipGroup(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .fillMaxWidth(),
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
        if (itemsData.value.isNullOrEmpty()){
            EmptyTransaction(Modifier.fillMaxSize())
        } else {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = stringResource(id = R.string.trx_percentage),
                style = raleway(
                    fontSize = 16,
                    weight = FontWeight.Bold
                )
            )
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                border = BorderStroke(1.dp, BlueSecondary),
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            ){
                Chart(
                    data = groupedData.value!!
                )
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = stringResource(id = R.string.expense_by_category),
                style = raleway(
                    fontSize = 16,
                    weight = FontWeight.Bold
                )
            )
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                border = BorderStroke(1.dp, BlueSecondary),
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            ){
                LazyColumn{
                    items(groupedData.value!!.count()){
                        CategoryItemView(data = groupedData.value!![it])
                    }
                }
            }
        }
    }
}