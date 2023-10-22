package com.dhandev.expenseeye.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.dhandev.expenseeye.ui.theme.BlueMain
import com.dhandev.expenseeye.ui.theme.BlueSecondary

@Composable
fun StepProgressIndicator(
    modifier: Modifier = Modifier,
    totalDots: Int,
    selectedIndex: Int,
    selectedColor: Color = BlueMain,
    unselectedColor: Color = BlueSecondary,
    dotSize: Dp
){
    LazyRow(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight()
    ){
        items(totalDots){index->
            IndicatorDot(
                size = dotSize,
                color = if (selectedIndex == index) selectedColor else unselectedColor,
                selected = selectedIndex != index
            )

            if (index != totalDots - 1){
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}

@Composable
private fun IndicatorDot(
    modifier: Modifier = Modifier,
    size: Dp,
    color: Color,
    selected: Boolean
){
    Box(
        modifier =
        if (selected)
            modifier
                .size(width = 2 * size, height = size)
                .clip(CircleShape)
                .background(color)
        else
            modifier
                .size(width = 6 * size, height = size)
                .clip(RoundedCornerShape(size))
                .background(color)
    )
}

@Preview
@Composable
fun MyStepIndicatorPreview(){
    Surface(Modifier.padding(8.dp),
        color = Color.White
    ) {
        StepProgressIndicator(totalDots = 5, selectedIndex = 0, dotSize = 8.dp)
    }
}