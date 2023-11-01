package com.dhandev.expenseeye.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dhandev.expenseeye.R
import com.dhandev.expenseeye.navigation.NavigationDestination
import com.dhandev.expenseeye.presentation.landing.MainViewModel
import com.dhandev.expenseeye.presentation.ui.component.BalanceCardView
import com.dhandev.expenseeye.presentation.ui.component.ChipGroup
import com.dhandev.expenseeye.presentation.ui.component.TitleSubtitle
import com.dhandev.expenseeye.ui.theme.BlueSecondary
import com.dhandev.expenseeye.ui.theme.RalewayFamily
import com.dhandev.expenseeye.ui.theme.raleway
import org.koin.androidx.compose.koinViewModel

object HomeDestination : NavigationDestination {
    override val route: String = "home"
    override val titleRes: Int = R.string.home
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = koinViewModel(),
) {
    val gradient = Brush.linearGradient(
        colors = listOf(BlueSecondary, MaterialTheme.colorScheme.background),
        start = Offset(800f, 0f),
        end = Offset(0f, 400f)
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        BalanceCardView()
        Text(
            modifier = Modifier.padding(top = 24.dp),
            text = stringResource(id = R.string.expense_list),
            style = raleway(
                fontSize = 16,
                weight = FontWeight.Bold
            )
        )
        ChipGroup(
            modifier = Modifier.padding(vertical = 8.dp),
            items = listOf("7 hari", "2 minggu", "1 bulan", "Periode pencatatan")){

        }
    }
}