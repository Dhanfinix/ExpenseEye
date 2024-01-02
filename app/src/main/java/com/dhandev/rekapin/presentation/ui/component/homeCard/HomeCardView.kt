package com.dhandev.rekapin.presentation.ui.component.homeCard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhandev.rekapin.presentation.ui.component.budgetCard.BudgetCard
import com.dhandev.rekapin.presentation.ui.component.budgetCard.BudgetCardState
import com.dhandev.rekapin.presentation.ui.component.totalBalanceCard.TotalBalanceCard
import com.dhandev.rekapin.presentation.ui.component.totalBalanceCard.TotalBalanceCardState
import com.dhandev.rekapin.ui.theme.RekapinTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeCardView(
    modifier: Modifier = Modifier,
    state: HomeCardState
){
    val lazyListState = rememberLazyListState()

    LazyRow(
        state = lazyListState,
        modifier = modifier,
        flingBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState)
    ){
        item{
            BudgetCard(
                modifier = Modifier
                    .fillParentMaxWidth(0.95f)
                    .padding(
                        end = 16.dp,
                        top = 16.dp,
                        start = 16.dp,
                        bottom = 16.dp
                    ),
                state = BudgetCardState(
                    state.budgetBalance,
                    state.budgetLeft,
                    state.isShowBalance
                ){ state.isShown(it) }
            )
        }
        item {
            TotalBalanceCard(
                modifier = Modifier
                    .fillParentMaxWidth(0.95f)
                    .padding(
                        end = 16.dp,
                        top = 16.dp,
                        start = 0.dp,
                        bottom = 16.dp
                    ),
                state = TotalBalanceCardState(
                    state.balance,
                    state.targetAchieved,
                    state.isShowBalance,
                    state.target
                ){ state.isShown(it)}
            )
        }
    }
}