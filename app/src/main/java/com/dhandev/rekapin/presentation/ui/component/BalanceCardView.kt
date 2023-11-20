package com.dhandev.rekapin.presentation.ui.component

import HiddenBalanceView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhandev.rekapin.R
import com.dhandev.rekapin.ui.theme.BlueMain
import com.dhandev.rekapin.ui.theme.RekapinTheme
import com.dhandev.rekapin.ui.theme.Gray
import com.dhandev.rekapin.ui.theme.raleway
import com.dhandev.rekapin.utils.StringUtil

@Composable
fun BalanceCardView(
    modifier: Modifier = Modifier,
    balance: Double,
    budgetLeft: Float
){
    val showBalance = remember { mutableStateOf(true) }
    Card(modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ){
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "This month's budget",
                    style = raleway(fontSize = 14, weight = FontWeight.Normal)
                )
                Image(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clickable { showBalance.value = !showBalance.value },
                    painter = painterResource(id = R.drawable.ic_eye_shown),
                    contentDescription = "Hide balance"
                )
            }
            if (showBalance.value){
                Text(
                    text = StringUtil.formatRp(balance.toString()),
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
                    .fillMaxWidth()
                    .height(12.dp)
                    .padding(top = 6.dp)
                    .clip(RoundedCornerShape(8.dp)),
                progress = budgetLeft,
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
            BalanceCardView(modifier = Modifier.padding(16.dp), 100000.0, 0.5f)
        }
    }
}