package com.dhandev.rekapin.presentation.ui.component

import HiddenBalanceView
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhandev.rekapin.R
import com.dhandev.rekapin.ui.theme.BlueMain
import com.dhandev.rekapin.ui.theme.Gray
import com.dhandev.rekapin.ui.theme.RekapinTheme
import com.dhandev.rekapin.ui.theme.raleway
import com.dhandev.rekapin.utils.StringUtil

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BalanceCardView(
    modifier: Modifier = Modifier,
    balance: Double,
    budgetBalance: Double,
    budgetLeft: Double,
    targetAchieved: Double,
    target: Double,
    isShowBalance: MutableState<Boolean>,
    isShown:(Boolean) -> Unit
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
                budgetBalance = budgetBalance,
                isShowBalance = isShowBalance.value,
                budgetLeft = budgetLeft,
                isShown = { isShown(it) }
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
                balance = balance,
                isShowBalance = isShowBalance.value,
                targetAchieved = targetAchieved,
                target = target,
                isShown = { isShown(it) }
            )
        }
    }
}

@Composable
fun BudgetCard(
    modifier: Modifier = Modifier,
    budgetBalance: Double,
    budgetLeft: Double,
    isShowBalance: Boolean,
    isShown: (Boolean) -> Unit
) {
    Card(modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ){
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceAround
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "This month's budget",
                    style = raleway(fontSize = 14, weight = FontWeight.Normal)
                )
                Icon(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clickable { isShown(!isShowBalance) },
                    painter = painterResource(id = if (isShowBalance) R.drawable.ic_eye_shown else R.drawable.ic_eye_hide),
                    contentDescription = "Hide balance",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            if (isShowBalance){
                Text(
                    text = StringUtil.formatRp(budgetBalance.toString()),
                    style = raleway(fontSize = 32, weight = FontWeight.Bold)
                )
            } else {
                HiddenBalanceView()
            }

            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = "${budgetLeft * 100}% budget left",
                style = raleway(fontSize = 12, weight = FontWeight.Normal)
            )
            LinearProgressIndicator(
                modifier = Modifier
                    .height(16.dp)
                    .padding(top = 6.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                progress = budgetLeft.toFloat(),
                color = BlueMain,
                trackColor = Gray
            )
        }
    }
}

@Composable
fun TotalBalanceCard(
    modifier: Modifier = Modifier,
    balance: Double,
    targetAchieved: Double,
    isShowBalance: Boolean,
    target: Double,
    isShown: (Boolean) -> Unit
) {
    Card(modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ){
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceAround
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Your total balance",
                    style = raleway(fontSize = 14, weight = FontWeight.Normal)
                )
                Icon(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clickable { isShown(!isShowBalance) },
                    painter = painterResource(id = if (isShowBalance) R.drawable.ic_eye_shown else R.drawable.ic_eye_hide),
                    contentDescription = "Hide balance",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            if (isShowBalance){
                Text(
                    text = StringUtil.formatRp(balance.toString()),
                    style = raleway(fontSize = 32, weight = FontWeight.Bold)
                )
            } else {
                HiddenBalanceView()
            }

            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = String.format("You've reached %s from %s", "${100 - (targetAchieved * 100)}%", StringUtil.formatRp(target.toString())), //"You've reach ${targetAchieved * 100}% to achieve target",
                style = raleway(fontSize = 12, weight = FontWeight.Normal)
            )
            LinearProgressIndicator(
                modifier = Modifier
                    .height(16.dp)
                    .padding(top = 6.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                progress = targetAchieved.toFloat(),
                color = BlueMain,
                trackColor = Gray
            )
        }
    }
}

@Preview
@Composable
fun PreviewBalanceCard(){
    RekapinTheme {
        Surface {
            BalanceCardView(modifier = Modifier.padding(16.dp), 100000.0, 1000000.0, 0.5, 0.5, 1000.0, remember {
                mutableStateOf(
                    false
                )
            }) { true }
        }
    }
}