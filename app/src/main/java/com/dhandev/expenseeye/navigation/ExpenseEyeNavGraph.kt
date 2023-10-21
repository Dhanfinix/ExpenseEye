package com.dhandev.expenseeye.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dhandev.expenseeye.presentation.home.HomeDestination
import com.dhandev.expenseeye.presentation.home.HomeScreen
import com.dhandev.expenseeye.presentation.report.ReportDestination
import com.dhandev.expenseeye.presentation.report.ReportScreen
import com.dhandev.expenseeye.presentation.settings.SettingsDestination
import com.dhandev.expenseeye.presentation.settings.SettingsScreen

@Composable
fun ExpenseEyeNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen()
        }
        composable(route = ReportDestination.route) {
            ReportScreen()
        }
        composable(route = SettingsDestination.route) {
            SettingsScreen()
        }
    }
}