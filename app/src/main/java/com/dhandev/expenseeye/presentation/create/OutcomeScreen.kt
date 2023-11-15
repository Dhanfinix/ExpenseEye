package com.dhandev.expenseeye.presentation.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dhandev.expenseeye.R
import com.dhandev.expenseeye.data.model.Transaction
import com.dhandev.expenseeye.presentation.ui.component.DatePickerView
import com.dhandev.expenseeye.presentation.ui.component.DropdownView
import com.dhandev.expenseeye.presentation.ui.component.NumberFieldView
import com.dhandev.expenseeye.presentation.ui.component.TextFieldView
import com.dhandev.expenseeye.ui.theme.MyRed
import com.dhandev.expenseeye.ui.theme.raleway
import com.dhandev.expenseeye.utils.Constants
import com.dhandev.expenseeye.utils.NumUtil.clearThousandFormat
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun OutcomeScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateViewModel = koinViewModel(),
    onSuccess: () -> Unit
) {
    val mCategory = Constants.categoryOutcomeName
    var nominal by remember { mutableStateOf("") }
    var trxName by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(mCategory[0]) }
    var trxDate by remember { mutableLongStateOf(System.currentTimeMillis()) }
    val scope = rememberCoroutineScope()


    Box(modifier = modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            NumberFieldView(title = stringResource(id = R.string.amount), value = { nominal = it })
            TextFieldView(title = stringResource(id = R.string.trx_name), value = { trxName = it })
            DropdownView(
                title = stringResource(id = R.string.category),
                category = mCategory,
                value = { selectedCategory = it })
            DatePickerView(title = stringResource(id = R.string.date), value = { trxDate = it })
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            onClick = {
                scope.launch {
                    viewModel.insert(
                        Transaction(
                            trxName = trxName,
                            amount = nominal.clearThousandFormat().toDouble(),
                            category = selectedCategory.name,
                            dateInMillis = trxDate,
                            isOutcome = true
                        )
                    )
                }.invokeOnCompletion {
                    onSuccess.invoke()
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MyRed,
                contentColor = Color.White
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_save_24),
                contentDescription = stringResource(id = R.string.save)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(id = R.string.save),
                style = raleway(
                    fontSize = 16,
                    weight = FontWeight.Medium
                ),
                color = Color.White
            )
        }

//        Button(onClick = {
//            viewModel.getAll().observe(lifeCycleOwner){
//                println("Saved data $it")
//            }
//        }) {
//            Text(text = "Cek Saved")
//        }
    }
}