package com.dhandev.expenseeye.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun raleway(fontSize: Int, weight: FontWeight) = TextStyle(
    color = MaterialTheme.colorScheme.onSurface,
    fontSize = fontSize.sp,
    fontFamily = RalewayFamily,
    fontWeight = weight
)