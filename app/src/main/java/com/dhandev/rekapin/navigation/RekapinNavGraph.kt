package com.dhandev.rekapin.navigation

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
import androidx.navigation.navArgument
import com.dhandev.rekapin.presentation.create.CreateDestination
import com.dhandev.rekapin.presentation.create.CreateScreen
import com.dhandev.rekapin.presentation.home.HomeDestination
import com.dhandev.rekapin.presentation.home.HomeScreen
import com.dhandev.rekapin.presentation.landing.AfterSplash
import com.dhandev.rekapin.presentation.landing.AfterSplashDestination
import com.dhandev.rekapin.presentation.landing.LandingDestination
import com.dhandev.rekapin.presentation.landing.LandingScreen
import com.dhandev.rekapin.presentation.landing.MainViewModel
import com.dhandev.rekapin.presentation.report.ReportDestination
import com.dhandev.rekapin.presentation.report.ReportScreen
import com.dhandev.rekapin.presentation.settings.SettingsDestination
import com.dhandev.rekapin.presentation.settings.SettingsScreen

@Composable
fun RekapinNavGraph(
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
            startDestination =  if (logged) AfterSplashDestination.route else LandingDestination.route,
            modifier = modifier
                .fillMaxSize()
        ) {
            composable(route = LandingDestination.route){
                LandingScreen(
                    autoSlideDuration = 5000,
                    navigateToHome = {navController.navigate(HomeDestination.route){
                        popUpTo(LandingDestination.route) { inclusive = true }
                    } }
                )
            }
            composable(route = AfterSplashDestination.route){
                AfterSplash(
                    goToHome = {navController.navigate(HomeDestination.route){
                        popUpTo(AfterSplashDestination.route) { inclusive = true }
                    } }
                )
            }
            composable(route = HomeDestination.route) {
                HomeScreen(
                    paddingValues = paddingValues,
                    navigateToCreate = {navController.navigate("${CreateDestination.route}?itemId=$it")})
            }
            composable(route = ReportDestination.route) {
                ReportScreen(
                    paddingValues = paddingValues
                )
                BackHandler {
                    navController.navigate(HomeDestination.route) {
                        popUpTo(HomeDestination.route) { inclusive = true }
                    }
                }
            }
            composable(route = SettingsDestination.route) {
                SettingsScreen(paddingValues = paddingValues)
                BackHandler {
                    navController.navigate(HomeDestination.route) {
                        popUpTo(HomeDestination.route) { inclusive = true }
                    }
                }
            }
            composable(
                route = CreateDestination.routeWithArgs,
                arguments = listOf(navArgument(CreateDestination.itemIdArg){
                    nullable = true
                })
            ){
                CreateScreen(
                    modifier = Modifier.padding(paddingValues),
                    navigateBack = { navController.popBackStack() },
                    argument = it.arguments?.getString(CreateDestination.itemIdArg)
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