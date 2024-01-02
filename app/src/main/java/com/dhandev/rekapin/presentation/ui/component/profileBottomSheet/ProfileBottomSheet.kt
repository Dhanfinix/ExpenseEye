package com.dhandev.rekapin.presentation.ui.component.profileBottomSheet

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dhandev.rekapin.R
import com.dhandev.rekapin.data.model.ProfileModel
import com.dhandev.rekapin.presentation.ui.component.NumberFieldView
import com.dhandev.rekapin.presentation.ui.component.NumberPickerView
import com.dhandev.rekapin.presentation.ui.component.TextFieldView
import com.dhandev.rekapin.ui.theme.BlueMain
import com.dhandev.rekapin.ui.theme.raleway
import com.dhandev.rekapin.utils.NumUtil.clearThousandFormat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileBottomSheet(
    modifier: Modifier = Modifier,
    title: String,
    sheetState: SheetState,
    scope: CoroutineScope,
    userData: ProfileModel? = null,
    onProceed: (ProfileModel) -> Unit,
    isShown: (Boolean) -> Unit
) {
    val profileState by remember { mutableStateOf(ProfileState()) }
    val context = LocalContext.current

    if (userData != null){
        profileState.mapModelToState(userData)
    }

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
                setData = profileState.userName,
                value = { profileState.userName = it }
            )
            NumberFieldView(
                modifier = modifier.padding(bottom = 12.dp),
                title = stringResource(id = R.string.landing_start_current_balance),
                setData = profileState.balance,
                value = { profileState.balance = it }
            )
            NumberFieldView(
                modifier = modifier.padding(bottom = 12.dp),
                title = stringResource(id = R.string.landing_start_monthly_budget),
                setData = profileState.budget,
                value = { profileState.budget = it }
            )
            NumberFieldView(
                modifier = modifier.padding(bottom = 12.dp),
                title = stringResource(id = R.string.landing_start_balance_target),
                setData = profileState.target,
                value = { profileState.target = it }
            )
            NumberPickerView(
                modifier = modifier.padding(bottom = 12.dp),
                title = stringResource(id = R.string.landing_start_period_date),
                setData = profileState.reportPeriod,
                value = { profileState.reportPeriod = it }
            )
            Button(
                onClick = {
                    if (profileState.userName.isNotEmpty() &&
                        profileState.balance.clearThousandFormat().isNotEmpty() &&
                        profileState.budget.clearThousandFormat().isNotEmpty() &&
                        profileState.target.clearThousandFormat().isNotEmpty()
                    ) {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            onProceed.invoke(
                                ProfileModel(
                                    profileState.userName,
                                    profileState.balance.clearThousandFormat().toDouble(),
                                    profileState.budget.clearThousandFormat().toDouble(),
                                    profileState.target.clearThousandFormat().toDouble(),
                                    profileState.reportPeriod
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