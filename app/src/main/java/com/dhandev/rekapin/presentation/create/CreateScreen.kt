package com.dhandev.rekapin.presentation.create

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.dhandev.rekapin.R
import com.dhandev.rekapin.navigation.NavigationDestination
import com.dhandev.rekapin.presentation.ui.component.TabView
import com.dhandev.rekapin.ui.theme.RekapinTheme

object CreateDestination : NavigationDestination {
    override val route: String = "createEdit"
    override val titleRes: Int = R.string.create
}

@Composable
fun CreateScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit
) {
    val context = LocalContext.current
    Box(modifier.fillMaxSize()) {
        TabView {
            Toast.makeText(context, R.string.transaction_success, Toast.LENGTH_SHORT).show()
            navigateBack()
        }
    }
}

@Preview
@Composable
fun PreviewCreate() {
    RekapinTheme {
        Surface {
            CreateScreen{}
        }
    }
}