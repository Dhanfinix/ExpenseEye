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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.dhandev.rekapin.data.model.CategoryItem
import com.dhandev.rekapin.data.model.TransactionItemModel
import com.dhandev.rekapin.presentation.ui.component.DatePickerView
import com.dhandev.rekapin.presentation.ui.component.DropdownView
import com.dhandev.rekapin.presentation.ui.component.NumberFieldView
import com.dhandev.rekapin.presentation.ui.component.TextFieldView
import com.dhandev.rekapin.ui.theme.BlueMain
import com.dhandev.rekapin.ui.theme.raleway
import com.dhandev.rekapin.utils.CategoryUtil
import com.dhandev.rekapin.utils.NumUtil.clearDot
import com.dhandev.rekapin.utils.NumUtil.clearThousandFormat
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun TransactionScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateViewModel = koinViewModel(),
    trxData: TransactionItemModel?,
    onSuccess: () -> Unit,
    mCategory: List<CategoryItem>,
    isExpense: Boolean
) {
    val trxState by remember { mutableStateOf(TransactionState()) }
    trxState.selectedCategory = mCategory[0]

    val scope = rememberCoroutineScope()
    val trxId = remember { mutableIntStateOf(0) }
    val saveEnabled = remember {mutableStateOf(false)}

    if (trxData != null && trxState.firstOpened) {
        trxId.intValue = trxData.id
        trxState.firstOpened = false
        trxState.nominal = trxData.total.toBigDecimal().toPlainString().clearDot().clearThousandFormat()
        trxState.trxName = trxData.title
        trxState.selectedCategory = CategoryUtil.findCategoryItemByName(trxData.category, isExpense) ?: mCategory[0]
        trxState.trxDate = trxData.dateInMillis
    }
    LaunchedEffect(trxState.nominal, trxState.trxName){
        saveEnabled.value = trxState.nominal != "" && trxState.trxName != ""
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            NumberFieldView(
                title = stringResource(id = R.string.amount),
                value = { trxState.nominal = it },
                setData = trxState.nominal
            )
            TextFieldView(
                title = stringResource(id = R.string.trx_name),
                value = { trxState.trxName = it },
                setData = trxState.trxName
            )
            DropdownView(
                title = stringResource(id = R.string.category),
                category = mCategory,
                value = { trxState.selectedCategory = it },
                setData = mCategory.indexOf(trxState.selectedCategory)
            )
            DatePickerView(
                title = stringResource(id = R.string.date),
                value = { trxState.trxDate = it },
                setData = trxState.trxDate
            )
        }
        Button(
            enabled = saveEnabled.value,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            onClick = {
                scope.launch {
                    val payload = TransactionItemModel(
                        id = trxId.intValue,
                        title = trxState.trxName,
                        total = trxState.nominal.clearThousandFormat().toDouble(),
                        category = trxState.selectedCategory?.name ?: "",
                        dateInMillis = trxState.trxDate,
                        isExpense = isExpense
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
                containerColor = BlueMain,
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
