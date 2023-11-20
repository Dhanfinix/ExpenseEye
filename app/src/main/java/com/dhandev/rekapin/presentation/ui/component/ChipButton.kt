package com.dhandev.rekapin.presentation.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dhandev.rekapin.ui.theme.BlueSecondary
import com.dhandev.rekapin.ui.theme.raleway


@Composable
fun ChipGroup(
    modifier: Modifier = Modifier,
    items: List<String>,
    selectedItem: MutableIntState,
    onItemSelected: (Int) -> Unit
) {
    LazyRow(
        modifier = modifier
    ) {
        itemsIndexed(items) { index, item ->
            ChipButton(
                modifier = Modifier.padding(start = if (index == 0)  16.dp else 0.dp),
                text = item,
                onClick = {
                    onItemSelected(index)
                },
                backgroundColor = if (selectedItem.intValue == index) BlueSecondary else Color.White,
                contentColor = Color.Black
            )
        }
    }
}

@Composable
private fun ChipButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    backgroundColor: Color = Color.White,
    contentColor: Color = Color.Black
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = backgroundColor,
        border = BorderStroke(1.dp, BlueSecondary),
        modifier = modifier.clickable(
            interactionSource = MutableInteractionSource(),
            onClick = onClick,
            indication = null
        ).padding(end = 8.dp)
    ) {
        Text(
            text = text,
            color = contentColor,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            style = raleway(fontSize = 10, weight = FontWeight.SemiBold)
        )
    }
}