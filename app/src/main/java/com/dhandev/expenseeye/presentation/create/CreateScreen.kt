package com.dhandev.expenseeye.presentation.create

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dhandev.expenseeye.R
import com.dhandev.expenseeye.navigation.NavigationDestination

object CreateDestination : NavigationDestination {
    override val route: String = "createEdit"
    override val titleRes: Int = R.string.create
}
@Composable
fun CreateScreen(
    modifier: Modifier = Modifier
) {

}