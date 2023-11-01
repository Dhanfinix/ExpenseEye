package com.dhandev.expenseeye.presentation.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhandev.expenseeye.data.model.TransactionItemModel
import com.dhandev.expenseeye.data.model.TransactionGroupModel
import com.dhandev.expenseeye.ui.theme.BlueSecondary
import com.dhandev.expenseeye.ui.theme.ExpenseEyeTheme
import com.dhandev.expenseeye.ui.theme.MyGreen
import com.dhandev.expenseeye.ui.theme.MyRed
import com.dhandev.expenseeye.ui.theme.raleway
import com.dhandev.expenseeye.utils.DateUtil
import com.dhandev.expenseeye.utils.StringUtil

@Composable
fun TransactionGroup(
    modifier: Modifier = Modifier,
    data: TransactionGroupModel
) {
    val totalTransaction = data.transactionItem.sumOf { if (it.isExpense) -it.total else it.total }

    val isExpense = totalTransaction < 0
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = White,
        border = BorderStroke(1.dp, BlueSecondary),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = DateUtil.millisToDateForGroup(data.dateInMillis),
                    style = raleway(fontSize = 14, weight = FontWeight.Normal)
                )
                Text(
                    text = StringUtil.formatRpWithSign(totalTransaction.toString(), isExpense),
                    style = raleway(fontSize = 14, weight = FontWeight.SemiBold),
                    color = if (isExpense) MyRed else MyGreen
                )
            }
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(100))
                    .border(2.dp, BlueSecondary)
            )
            Column{
                data.transactionItem.forEach {
                    TransactionItemView(data = it)
                }
            }
        }

    }
}

@Preview
@Composable
fun PreviewTrxGroup() {
    ExpenseEyeTheme {
        Surface {
            TransactionGroup(modifier = Modifier.padding(8.dp), dummyData)
        }
    }
}


val dummyData = TransactionGroupModel(
    id = 1,
    dateInMillis = System.currentTimeMillis(),
    transactionItem = listOf(
        TransactionItemModel(
            id = 1,
            title = "Groceries",
            dateInMillis = System.currentTimeMillis(),
            total = 10000.0,
            category = "Food",
            isExpense = false
        ),
        TransactionItemModel(
            id = 2,
            title = "Salary",
            dateInMillis = System.currentTimeMillis(),
            total = 1000.0,
            category = "Income",
            isExpense = false
        )
    )
)

val dummyData1 = TransactionGroupModel(
    id = 2,
    dateInMillis = System.currentTimeMillis(),
    transactionItem = listOf(
        TransactionItemModel(
            id = 3,
            title = "Rent",
            dateInMillis = System.currentTimeMillis(),
            total = 5000.0,
            category = "Income",
            isExpense = true
        ),
        TransactionItemModel(
            id = 4,
            title = "Freelance",
            dateInMillis = System.currentTimeMillis(),
            total = 2000.0,
            category = "Income",
            isExpense = false
        )
    )
)

val dummyData2 = TransactionGroupModel(
    id = 3,
    dateInMillis = System.currentTimeMillis(),
    transactionItem = listOf(
        TransactionItemModel(
            id = 5,
            title = "Utilities",
            dateInMillis = System.currentTimeMillis(),
            total = 1500.0,
            category = "Bills",
            isExpense = true
        ),
        TransactionItemModel(
            id = 6,
            title = "Dividends",
            dateInMillis = System.currentTimeMillis(),
            total = 500.0,
            category = "Income",
            isExpense = false
        )
    )
)

val dummyData3 = TransactionGroupModel(
    id = 4,
    dateInMillis = System.currentTimeMillis(),
    transactionItem = listOf(
        TransactionItemModel(
            id = 7,
            title = "Utilities",
            dateInMillis = System.currentTimeMillis(),
            total = 1500.0,
            category = "Bills",
            isExpense = true
        ),
        TransactionItemModel(
            id = 8,
            title = "Dividends",
            dateInMillis = System.currentTimeMillis(),
            total = 500.0,
            category = "Income",
            isExpense = false
        )
    )
)

val listDummyDataTransaction = listOf(dummyData, dummyData1, dummyData2, dummyData3, dummyData, dummyData1, dummyData2, dummyData3, dummyData, dummyData1, dummyData2, dummyData3)

