package com.dhandev.rekapin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.dhandev.rekapin.presentation.RekapinApp
import com.dhandev.rekapin.presentation.landing.MainViewModel
import com.dhandev.rekapin.ui.theme.RekapinTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            val darkTheme = remember { mutableStateOf(true) }
            val viewModel : MainViewModel = koinViewModel()
            viewModel.getTheme()
            val navController = rememberNavController()
            viewModel.isDark.observe(this){
                darkTheme.value = it
            }
            RekapinTheme(darkTheme.value, dynamicColor = false) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RekapinApp(navController, viewModel)
                }
            }
        }
    }
}