package com.dhandev.rekapin.presentation.ui.component

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dhandev.rekapin.R
import com.dhandev.rekapin.data.model.ProfileModel
import com.dhandev.rekapin.ui.theme.BlueMain
import com.dhandev.rekapin.ui.theme.raleway
import com.dhandev.rekapin.utils.NumUtil.clearThousandFormat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingBottomSheet(
    modifier: Modifier = Modifier,
    title: String,
    sheetState: SheetState,
    scope: CoroutineScope,
    onProceed: (ProfileModel) -> Unit,
    isShown: (Boolean) -> Unit
) {
    var username by remember { mutableStateOf("") }
    var balance by remember { mutableStateOf("0") }
    var budget by remember { mutableStateOf("0") }
    var target by remember { mutableStateOf("0") }
    var reportPeriod by remember { mutableIntStateOf(1) }
    val context = LocalContext.current

    ModalBottomSheet(
        modifier = modifier.fillMaxSize(),
        onDismissRequest = { isShown(false) },
        sheetState = sheetState,
    ) {
        // Sheet content
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = modifier.padding(bottom = 12.dp),
                text = title,
                style = raleway(fontSize = 16, weight = FontWeight.Bold)
            )
            TextFieldView(
                modifier = modifier.padding(bottom = 12.dp),
                title = stringResource(id = R.string.landing_start_name),
                value = { username = it }
            )
            NumberFieldView(
                modifier = modifier.padding(bottom = 12.dp),
                title = stringResource(id = R.string.landing_start_current_balance),
                value = { balance = it }
            )
            NumberFieldView(
                modifier = modifier.padding(bottom = 12.dp),
                title = stringResource(id = R.string.landing_start_monthly_budget),
                value = { budget = it }
            )
            NumberFieldView(
                modifier = modifier.padding(bottom = 12.dp),
                title = stringResource(id = R.string.landing_start_balance_target),
                value = { target = it }
            )
            NumberPickerView(
                modifier = modifier.padding(bottom = 12.dp),
                title = stringResource(id = R.string.landing_start_period_date),
                value = { reportPeriod = it }
            )
            Button(
                onClick = {
                    if (username.isNotEmpty() &&
                        balance.clearThousandFormat().isNotEmpty() &&
                        budget.clearThousandFormat().isNotEmpty() &&
                        target.clearThousandFormat().isNotEmpty()
                    ) {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            onProceed.invoke(
                                ProfileModel(
                                    username,
                                    balance.clearThousandFormat().toLong(),
                                    budget.clearThousandFormat().toLong(),
                                    target.clearThousandFormat().toLong(),
                                    reportPeriod
                                )
                            )
                        }
                    } else {
                        Toast.makeText(context, "Mohon isi semua kolom", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BlueMain,
                    contentColor = Color.White
                )
            ) {
                Text(text = stringResource(id = R.string.landing_start_btn))
            }
        }
    }
}