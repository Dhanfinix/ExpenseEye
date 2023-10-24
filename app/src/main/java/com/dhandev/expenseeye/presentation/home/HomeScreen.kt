package com.dhandev.expenseeye.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dhandev.expenseeye.R
import com.dhandev.expenseeye.navigation.NavigationDestination
import com.dhandev.expenseeye.presentation.landing.MainViewModel
import com.dhandev.expenseeye.presentation.ui.component.BalanceCardView
import com.dhandev.expenseeye.presentation.ui.component.TitleSubtitle
import org.koin.androidx.compose.koinViewModel

object HomeDestination : NavigationDestination{
    override val route: String = "home"
    override val titleRes: Int = R.string.home
}
@Composable
fun HomeScreen(
    modifier : Modifier = Modifier,
    viewModel: MainViewModel = koinViewModel(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding()
    ) {
        BalanceCardView(modifier = Modifier.padding(16.dp))
    }
}