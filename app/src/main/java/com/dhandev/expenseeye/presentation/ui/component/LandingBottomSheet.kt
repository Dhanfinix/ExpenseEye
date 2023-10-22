package com.dhandev.expenseeye.presentation.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.dhandev.expenseeye.R
import com.dhandev.expenseeye.ui.theme.raleway
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    scope: CoroutineScope,
    onProceed: () -> Unit,
    isShown: (Boolean) -> Unit
) {
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
                text = stringResource(id = R.string.landing_start_title),
                style = raleway(fontSize = 16, weight = FontWeight.Bold)
            )
            TextFieldView(
                modifier = modifier.padding(bottom = 12.dp),
                title = stringResource(id = R.string.landing_start_name),
                keyboardType = KeyboardType.Text
            )
            TextFieldView(
                modifier = modifier.padding(bottom = 12.dp),
                title = stringResource(id = R.string.landing_start_current_balance),
                keyboardType = KeyboardType.Number
            )
            DatePickerView(
                modifier = modifier.padding(bottom = 12.dp),
                title = stringResource(id = R.string.landing_start_period_date)
            )
            Button(
                onClick = { scope.launch { sheetState.hide() }.invokeOnCompletion {
//                    if (!sheetState.isVisible) {
//                        isShown(false)
//                    }
                    onProceed.invoke()
                } },
                modifier = Modifier
                    .padding(vertical = 22.dp)
                    .fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.landing_start_btn))
            }
        }
    }
}