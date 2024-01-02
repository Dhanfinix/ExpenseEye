package com.dhandev.rekapin.presentation.settings

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dhandev.rekapin.BuildConfig
import com.dhandev.rekapin.R
import com.dhandev.rekapin.data.model.SettingsModel
import com.dhandev.rekapin.data.model.TransactionItemModel
import com.dhandev.rekapin.navigation.NavigationDestination
import com.dhandev.rekapin.presentation.create.CreateViewModel
import com.dhandev.rekapin.presentation.landing.MainViewModel
import com.dhandev.rekapin.presentation.ui.component.profileBottomSheet.ProfileBottomSheet
import com.dhandev.rekapin.presentation.ui.component.MyAlertDialog
import com.dhandev.rekapin.presentation.ui.component.SwitchButton
import com.dhandev.rekapin.ui.theme.BlueMain
import com.dhandev.rekapin.ui.theme.BlueSecondary
import com.dhandev.rekapin.ui.theme.raleway
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.io.File
import java.io.FileWriter

object SettingsDestination : NavigationDestination {
    override val route: String = "settings"
    override val titleRes: Int = R.string.settings
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    viewModel: MainViewModel = koinViewModel(),
    navigateToHome: () -> Unit
) {
    val context = LocalContext.current
    val lifeCycleOwner = LocalLifecycleOwner.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    val openAlertDialog = remember { mutableStateOf("") }
    val doExport = remember { mutableStateOf(false) }
    val doImport = remember { mutableStateOf(false) }
    val exportData by viewModel.getAll(0).observeAsState()

    viewModel.getTheme()
    var isDarkMode by remember { mutableStateOf(false) }
    viewModel.isDark.observe(lifeCycleOwner){
        isDarkMode = it
    }
    val isEnglish = remember { mutableStateOf(false) }
    LaunchedEffect(isDarkMode){
        viewModel.getTheme()
    }
    val settingsItems = listOf(
        SettingsModel(R.drawable.ic_profile, R.string.edit_profile, false) {
            showBottomSheet = true
        },
        SettingsModel(
            R.drawable.ic_dark_mode,
            R.string.dark_theme,
            switchState = isDarkMode
        ) {
            isDarkMode = !isDarkMode
            viewModel.saveTheme(isDarkMode)
        },
//        SettingsModel(R.drawable.ic_language, R.string.change_lang, switchState = isEnglish.value) {
//            showToast(
//                context,
//                "Language"
//            )
//        },
        SettingsModel(R.drawable.ic_import, R.string.import_data, false) {
            openAlertDialog.value = "Import"
        },
        SettingsModel(R.drawable.ic_export, R.string.export_data, false) {
            openAlertDialog.value = "Export"
        },
        SettingsModel(R.drawable.ic_logout, R.string.logout, false) {
            openAlertDialog.value = "Logout"
        }
    )
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.settings),
                    style = raleway(20, FontWeight.Bold)
                )
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = raleway(16, FontWeight.Normal)
                )
            }
            settingsItems.forEach { item ->
                SwitchButton(
                    icon = item.icon,
                    title = item.title,
                    showSwitch = item.showSwitch,
                    onSwitchButtonClick = item.action,
                    switchState = item.switchState
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_icon_app),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(30.dp)
                )

                Spacer(
                    modifier = Modifier
                        .height(40.dp)
                        .width(5.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(color = BlueSecondary)
                        .offset(x = (-20).dp)
                )
                Text(
                    text = stringResource(id = R.string.app_name),
                    modifier = Modifier
                        .padding(start = 8.dp),
                    style = raleway(fontSize = 20, weight = FontWeight.SemiBold),
                    color = BlueMain
                )
            }
            Text(
                text = stringResource(id = R.string.version, BuildConfig.VERSION_NAME),
                style = raleway(16, FontWeight.Normal),
            )
        }
        if (showBottomSheet) {
            ProfileBottomSheet(
                sheetState = sheetState,
                scope = scope,
                title = stringResource(id = R.string.edit_profile),
                userData = viewModel.userData,
                onProceed = {
                    viewModel.saveProfileData(it, true)
                    viewModel.updateInitialBalance(it.balance)
                    navigateToHome.invoke()
                },
                isShown = { showBottomSheet = it })
        }
        when (openAlertDialog.value) {
            "Logout" -> MyAlertDialog(
                onDismissRequest = { openAlertDialog.value = "" },
                onConfirmation = { scope.launch { viewModel.logout() } },
                dialogTitle = stringResource(id = R.string.logout),
                dialogText = stringResource(id = R.string.logout_confrimation),
                icon = Icons.Filled.Warning
            )
            "Export" -> MyAlertDialog(
                onDismissRequest = { openAlertDialog.value = "" },
                onConfirmation = {
                    doExport.value = true
                    openAlertDialog.value = ""
                },
                dialogTitle = "Export Data",
                dialogText = "Are you sure to export transaction data in .json file format?\nThis file only readable by this app",
                icon = Icons.Filled.Info
            )
            "Import" -> MyAlertDialog(
                onDismissRequest = { openAlertDialog.value = "" },
                onConfirmation = {
                    doImport.value = true
                    openAlertDialog.value = ""
                },
                dialogTitle = "Import Data",
                dialogText = "Importing Data only possible if the file format is in .json",
                icon = Icons.Filled.Info
            )
        }
        if (doExport.value) {
            if (exportData != null) {
                ExportData(context, exportData!!) { doExport.value = it }
            }
        }
        if (doImport.value) {
            ImportData(doImport = {doImport.value = it}){
                navigateToHome()
            }
        }
    }
}

@Composable
fun ExportData(context: Context, data: List<TransactionItemModel>, doExport: (Boolean) -> Unit) {
    val json = Gson().toJson(data)
    val currentDate = System.currentTimeMillis()
    val file = File(context.filesDir, "Rekapin_$currentDate.json")
    val writer = FileWriter(file)
    writer.use { it.write(json) }
    val createDocument =
        rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument()) { uri ->
            uri?.let {
                context.contentResolver.openOutputStream(it)?.use { outputStream ->
                    outputStream.bufferedWriter().use { writer ->
                        writer.write(json)
                        doExport(false)
                    }
                }
            }
        }
    LaunchedEffect(Unit) {
        createDocument.launch("Rekapin_$currentDate.json")
    }
}
@Composable
fun ImportData(
    viewModel: CreateViewModel = koinViewModel(),
    doImport: (Boolean) -> Unit,
    onFinish: ()->Unit
) {
    val context = LocalContext.current
    val openDocument = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        uri?.let {
            context.contentResolver.openInputStream(it)?.use { inputStream ->
                inputStream.bufferedReader().use { reader ->
                    val content = reader.readText()
                    val listType = object : TypeToken<List<TransactionItemModel>>() {}.type
                    val data = Gson().fromJson<List<TransactionItemModel>>(content, listType)
                    viewModel.insertAll(data)
                    doImport(false)
                    onFinish()
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        openDocument.launch(arrayOf("*/*"))
    }
}

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}