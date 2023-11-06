package com.dhandev.expenseeye

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.dhandev.expenseeye.presentation.ExpenseEyeApp
import com.dhandev.expenseeye.presentation.landing.MainViewModel
import com.dhandev.expenseeye.ui.theme.ExpenseEyeTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
//        enableEdgeToEdge()
        setContent {
            ExpenseEyeTheme(dynamicColor = false) {
                val viewModel : MainViewModel = koinViewModel()
                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ExpenseEyeApp(navController, viewModel)
                }
            }
        }
    }
}