package com.dhandev.rekapin.presentation.create

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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dhandev.rekapin.R
import com.dhandev.rekapin.data.model.TransactionItemModel
import com.dhandev.rekapin.presentation.ui.component.DatePickerView
import com.dhandev.rekapin.presentation.ui.component.DropdownView
import com.dhandev.rekapin.presentation.ui.component.NumberFieldView
import com.dhandev.rekapin.presentation.ui.component.TextFieldView
import com.dhandev.rekapin.ui.theme.MyRed
import com.dhandev.rekapin.ui.theme.raleway
import com.dhandev.rekapin.utils.CategoryUtil
import com.dhandev.rekapin.utils.Constants
import com.dhandev.rekapin.utils.NumUtil.clearThousandFormat
import com.dhandev.rekapin.utils.NumUtil.formatThousand
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun OutcomeScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateViewModel = koinViewModel(),
    trxData: TransactionItemModel?,
    onSuccess: () -> Unit
) {
    val firstOpened = remember { mutableStateOf(true) }
    val mCategory = Constants.categoryOutcomeName
    val nominal = remember { mutableStateOf("") }
    val trxName = remember { mutableStateOf("") }
    val selectedCategory = remember { mutableStateOf(mCategory[0]) }
    val trxDate = remember { mutableLongStateOf(System.currentTimeMillis()) }
    val scope = rememberCoroutineScope()
    val trxId = remember { mutableIntStateOf(0) }

    if (trxData != null && firstOpened.value) {
        firstOpened.value = false
        trxId.intValue = trxData.id
        nominal.value = trxData.total.toLong().formatThousand()
        trxName.value = trxData.title
        selectedCategory.value =
            CategoryUtil.findCategoryItemByName(trxData.title, true) ?: mCategory[0]
        trxDate.longValue = trxData.dateInMillis
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            NumberFieldView(
                title = stringResource(id = R.string.amount),
                value = { nominal.value = it },
                setData = nominal.value
            )
            TextFieldView(
                title = stringResource(id = R.string.trx_name),
                value = { trxName.value = it },
                setData = trxName.value
            )
            DropdownView(
                title = stringResource(id = R.string.category),
                category = mCategory,
                value = { selectedCategory.value = it },
                setData = mCategory.indexOf(selectedCategory.value)
            )
            DatePickerView(
                title = stringResource(id = R.string.date),
                value = { trxDate.longValue = it },
                setData = trxDate.longValue
            )
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            onClick = {
                scope.launch {
                    val payload = TransactionItemModel(
                        id = trxId.intValue,
                        title = trxName.value,
                        total = nominal.value.clearThousandFormat().toDouble(),
                        category = selectedCategory.value.name,
                        dateInMillis = trxDate.longValue,
                        isExpense = true
                    )
                    if (trxData != null){
                        viewModel.update(payload)
                    } else {
                        viewModel.insert(payload)
                    }

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
    }
}