package com.dhandev.expenseeye.presentation.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.dhandev.expenseeye.R
import com.dhandev.expenseeye.presentation.ui.component.DatePickerView
import com.dhandev.expenseeye.presentation.ui.component.DropdownView
import com.dhandev.expenseeye.presentation.ui.component.NumberFieldView
import com.dhandev.expenseeye.presentation.ui.component.TextFieldView
import com.dhandev.expenseeye.utils.Constants

@Composable
fun OutcomeScreen(
    modifier: Modifier = Modifier
) {
    val mCategory = Constants.categoryOutcomeName
    var nominal by remember { mutableStateOf("") }
    var trxName by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(mCategory[0]) }
    var trxDate by remember { mutableLongStateOf(System.currentTimeMillis()) }

    Column(
        modifier = modifier,
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
}