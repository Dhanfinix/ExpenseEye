package com.dhandev.rekapin.presentation.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dhandev.rekapin.R
import com.dhandev.rekapin.navigation.NavigationDestination

object SettingsDestination : NavigationDestination{
    override val route: String = "settings"
    override val titleRes: Int = R.string.settings
}
@Composable
fun SettingsScreen(
    modifier : Modifier = Modifier
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "This is Settings Screen")
    }
}