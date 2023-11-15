package com.dhandev.expenseeye.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dhandev.expenseeye.presentation.create.CreateDestination
import com.dhandev.expenseeye.presentation.create.CreateScreen
import com.dhandev.expenseeye.presentation.home.HomeDestination
import com.dhandev.expenseeye.presentation.home.HomeScreen
import com.dhandev.expenseeye.presentation.landing.LandingDestination
import com.dhandev.expenseeye.presentation.landing.LandingScreen
import com.dhandev.expenseeye.presentation.landing.MainViewModel
import com.dhandev.expenseeye.presentation.report.ReportDestination
import com.dhandev.expenseeye.presentation.report.ReportScreen
import com.dhandev.expenseeye.presentation.settings.SettingsDestination
import com.dhandev.expenseeye.presentation.settings.SettingsScreen

@Composable
fun ExpenseEyeNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    viewModel: MainViewModel
) {
    val logged by viewModel.logged
    val loading by viewModel.loading
    if (loading){
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        NavHost(
            navController = navController,
            startDestination =  if (logged) HomeDestination.route else LandingDestination.route,
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            composable(route = LandingDestination.route){
                LandingScreen(
                    autoSlideDuration = 5000,
                    navigateToHome = {navController.navigate(HomeDestination.route){
                        popUpTo(LandingDestination.route) { inclusive = true }
                    } }
                )
            }
            composable(route = HomeDestination.route) {
                HomeScreen(navigateToCreate = {navController.navigate(CreateDestination.route)})
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
            composable(route = CreateDestination.route){
                CreateScreen(
                    navigateBack = { navController.popBackStack() },
                )
                BackHandler {
                    navController.navigate(HomeDestination.route) {
                        popUpTo(HomeDestination.route) { inclusive = true }
                    }
                }
            }
        }
    }
}