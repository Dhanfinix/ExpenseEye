package com.dhandev.expenseeye.presentation.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dhandev.expenseeye.R
import com.dhandev.expenseeye.navigation.NavigationDestination

object HomeDestination : NavigationDestination{
    override val route: String = "home"
    override val titleRes: Int = R.string.app_name
}
@Composable
fun HomeScreen(
    modifier : Modifier = Modifier
) {
    Text(text = "This is Navigation Power")
}