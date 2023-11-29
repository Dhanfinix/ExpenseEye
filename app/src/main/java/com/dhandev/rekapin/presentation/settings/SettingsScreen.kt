package com.dhandev.rekapin.presentation.settings

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dhandev.rekapin.R
import com.dhandev.rekapin.data.model.SettingsModel
import com.dhandev.rekapin.navigation.NavigationDestination
import com.dhandev.rekapin.presentation.ui.component.SwitchButton
import com.dhandev.rekapin.ui.theme.raleway

object SettingsDestination : NavigationDestination {
    override val route: String = "settings"
    override val titleRes: Int = R.string.settings
}

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues
) {
    val context = LocalContext.current
    val settingsItems = listOf(
        SettingsModel(R.drawable.ic_profile, R.string.edit_profile, false) {
            showToast(
                context,
                "Profile"
            )
        },
        SettingsModel(R.drawable.ic_dark_mode, R.string.dark_theme) {
            showToast(
                context,
                "Dark Theme"
            )
        },
        SettingsModel(R.drawable.ic_language, R.string.change_lang) {
            showToast(
                context,
                "Language"
            )
        },
        SettingsModel(R.drawable.ic_import, R.string.import_data, false) {
            showToast(
                context,
                "Import"
            )
        },
        SettingsModel(R.drawable.ic_export, R.string.export_data, false) {
            showToast(
                context,
                "Export"
            )
        },
        SettingsModel(R.drawable.ic_logout, R.string.logout, false) { showToast(context, "Logout") }
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(text = stringResource(id = R.string.settings), style = raleway(20, FontWeight.Bold))
            Text(text = stringResource(id = R.string.app_name), style = raleway(16, FontWeight.Normal))
        }
        settingsItems.forEach { item ->
            SwitchButton(
                icon = item.icon,
                title = item.title,
                showSwitch = item.showSwitch,
                onSwitchButtonClick = item.action
            )
        }
    }
}
fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}