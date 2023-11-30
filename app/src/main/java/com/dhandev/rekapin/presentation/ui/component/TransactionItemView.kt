package com.dhandev.rekapin.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhandev.rekapin.R
import com.dhandev.rekapin.data.model.TransactionItemModel
import com.dhandev.rekapin.ui.theme.RekapinTheme
import com.dhandev.rekapin.ui.theme.MyGreen
import com.dhandev.rekapin.ui.theme.MyRed
import com.dhandev.rekapin.ui.theme.raleway
import com.dhandev.rekapin.utils.CategoryUtil
import com.dhandev.rekapin.utils.StringUtil
import com.dhandev.rekapin.utils.TransactionCategory

@Composable
fun TransactionItemView(
    modifier: Modifier = Modifier,
    data: TransactionItemModel,
    clicked:()->Unit
) {
    val (categoryImage, categoryName) = CategoryUtil.getCategory(data.category)

    Row(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                clicked()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(categoryImage),
            contentDescription = stringResource(id = R.string.category_image_desc)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        ) {
            Text(text = data.title, style = raleway(fontSize = 12, weight = FontWeight.Medium))
            Text(
                text = TransactionCategory.values()[categoryName].toString(),
                style = raleway(fontSize = 10, weight = FontWeight.Normal)
            )
        }
        Text(
            text = StringUtil.formatRpWithSign(data.total.toString(), data.isExpense),
            style = raleway(fontSize = 14, weight = FontWeight.SemiBold),
            color = if (data.isExpense) MyRed else MyGreen
        )
    }
}

@Preview
@Composable
fun PreviewItem() {
    RekapinTheme {
        Surface {
            TransactionItemView(
                data = TransactionItemModel(
                    0,
                    "Apa ya",
                    10012112,
                    100000.0,
                    TransactionCategory.Health.toString(),
                    false
                ),
                clicked = {}
            )
        }
    }
}