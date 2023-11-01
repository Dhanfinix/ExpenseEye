/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dhandev.expenseeye.presentation

import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.dhandev.expenseeye.R
import com.dhandev.expenseeye.navigation.ExpenseEyeNavGraph
import com.dhandev.expenseeye.presentation.home.HomeDestination
import com.dhandev.expenseeye.presentation.landing.LandingDestination
import com.dhandev.expenseeye.presentation.landing.MainViewModel
import com.dhandev.expenseeye.presentation.report.ReportDestination
import com.dhandev.expenseeye.presentation.settings.SettingsDestination
import com.dhandev.expenseeye.presentation.ui.component.BottomNavigationView
import com.dhandev.expenseeye.presentation.ui.component.TitleSubtitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseEyeApp(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val currentRouteName = navBackStackEntry?.destination?.displayName
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            when (currentRoute) {
                HomeDestination.route -> TitleSubtitle(title = "Good Morning,", subtitle = viewModel.username.value, scrollBehavior = scrollBehavior)
                ReportDestination.route -> TitleSubtitle(title = "Here is your money report,", subtitle = "in good visual", scrollBehavior = scrollBehavior)
                SettingsDestination.route -> TitleSubtitle(title = "Settings", subtitle = stringResource(
                    id = R.string.app_name
                ), scrollBehavior = scrollBehavior)

                else -> {}
                //            else -> ExpenseEyeTopAppBar(title = currentRouteName, canNavigateBack = true)
            }
        },
        bottomBar = {
            if (currentRoute != LandingDestination.route) {
                BottomNavigationView(navController = navController, Modifier)
            }
        },
        content = { paddingValues ->
            ExpenseEyeNavGraph(navController = navController, Modifier, paddingValues, viewModel)
        }
    )
}

/**
 * App bar to display title and conditionally display the back navigation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseEyeTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}
