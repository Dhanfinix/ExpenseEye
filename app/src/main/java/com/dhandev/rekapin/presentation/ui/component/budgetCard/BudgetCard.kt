package com.dhandev.rekapin.presentation.ui.component.budgetCard

import HiddenBalanceView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dhandev.rekapin.R
import com.dhandev.rekapin.ui.theme.BlueMain
import com.dhandev.rekapin.ui.theme.Gray
import com.dhandev.rekapin.ui.theme.raleway
import com.dhandev.rekapin.utils.StringUtil

@Composable
fun BudgetCard(
    modifier: Modifier = Modifier,
    state: BudgetCardState
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
                        .clickable { state.isShown(!state.isShowBalance.value) },
                    painter = painterResource(id = if (state.isShowBalance.value) R.drawable.ic_eye_shown else R.drawable.ic_eye_hide),
                    contentDescription = "Hide balance",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            if (state.isShowBalance.value){
                Text(
                    text = StringUtil.formatRp(state.budgetBalance.doubleValue.toString()),
                    style = raleway(fontSize = 32, weight = FontWeight.Bold)
                )
            } else {
                HiddenBalanceView()
            }

            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = "${state.budgetLeft.doubleValue * 100}% budget left",
                style = raleway(fontSize = 12, weight = FontWeight.Normal)
            )
            LinearProgressIndicator(
                modifier = Modifier
                    .height(16.dp)
                    .padding(top = 6.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                progress = state.budgetLeft.doubleValue.toFloat(),
                color = BlueMain,
                trackColor = Gray
            )
        }
    }
}