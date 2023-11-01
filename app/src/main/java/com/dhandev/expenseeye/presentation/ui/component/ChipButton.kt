package com.dhandev.expenseeye.presentation.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dhandev.expenseeye.ui.theme.BlueSecondary
import com.dhandev.expenseeye.ui.theme.raleway


@Composable
fun ChipGroup(
    modifier: Modifier = Modifier,
    items: List<String>,
    onItemSelected: (Int) -> Unit
) {
    val selectedItem = remember { mutableIntStateOf(0) }
    LazyRow(
        modifier = modifier
    ) {
        itemsIndexed(items) { index, item ->
            ChipButton(
                text = item,
                onClick = {
                    selectedItem.intValue = index
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
    text: String,
    onClick: () -> Unit,
    backgroundColor: Color = Color.White,
    contentColor: Color = Color.Black
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = backgroundColor,
        border = BorderStroke(1.dp, BlueSecondary),
        modifier = Modifier.clickable(onClick = onClick).padding(end = 8.dp)
    ) {
        Text(
            text = text,
            color = contentColor,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            style = raleway(fontSize = 10, weight = FontWeight.SemiBold)
        )
    }
}