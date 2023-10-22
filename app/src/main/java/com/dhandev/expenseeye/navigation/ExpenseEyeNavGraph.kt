package com.dhandev.expenseeye.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dhandev.expenseeye.presentation.home.HomeDestination
import com.dhandev.expenseeye.presentation.home.HomeScreen
import com.dhandev.expenseeye.presentation.landing.LandingDestination
import com.dhandev.expenseeye.presentation.landing.LandingScreen
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
    val logged = false
    NavHost(
        navController = navController,
        startDestination = if (logged) HomeDestination.route else LandingDestination.route,
        modifier = modifier
    ) {
        composable(route = LandingDestination.route){
            LandingScreen(autoSlideDuration = 5000)
        }
        composable(route = HomeDestination.route) {
            HomeScreen()
        }
        composable(route = ReportDestination.route) {
            ReportScreen()
            BackHandler {
                navController.navigate(HomeDestination.route) {
                    popUpTo(HomeDestination.route) { inclusive = true }
                }
            }
        }
        composable(route = SettingsDestination.route) {
            SettingsScreen()
            BackHandler {
                navController.navigate(HomeDestination.route) {
                    popUpTo(HomeDestination.route) { inclusive = true }
                }
            }
        }
    }
}