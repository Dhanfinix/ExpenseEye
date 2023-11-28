package com.dhandev.rekapin.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dhandev.rekapin.R
import com.dhandev.rekapin.data.model.CategoryGroupModel
import com.dhandev.rekapin.ui.theme.MyGreen
import com.dhandev.rekapin.ui.theme.MyRed
import com.dhandev.rekapin.ui.theme.raleway
import com.dhandev.rekapin.utils.CategoryUtil
import com.dhandev.rekapin.utils.StringUtil
import kotlin.math.roundToInt

@Composable
fun CategoryItemView(
    modifier: Modifier = Modifier,
    data: CategoryGroupModel,
    clickedCategory:(Int)-> Unit
) {
    val (categoryImage, categoryName) = CategoryUtil.getCategory(data.category)

    Row(
        modifier = Modifier
            .padding(16.dp)
            .clickable { clickedCategory(categoryName) },
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
            Text(text = data.category, style = raleway(fontSize = 12, weight = FontWeight.Medium))
            Text(
                text = StringUtil.formatRpWithSign(data.total.toString(), data.total < 0),
                style = raleway(fontSize = 10, weight = FontWeight.Normal),
                color = if (data.total < 0) MyRed else MyGreen
            )
        }
        Text(
            text = "${if (data.total < 0) data.proportion.roundToInt() * -1 else data.proportion.roundToInt()}%",
            style = raleway(fontSize = 14, weight = FontWeight.SemiBold),
            color = if (data.total < 0) MyRed else MyGreen
        )
    }
}