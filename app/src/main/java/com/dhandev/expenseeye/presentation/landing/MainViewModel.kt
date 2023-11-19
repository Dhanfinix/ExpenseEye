package com.dhandev.expenseeye.presentation.landing

import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dhandev.expenseeye.data.local.DataStorePreference
import com.dhandev.expenseeye.data.model.CategoryItem
import com.dhandev.expenseeye.data.model.ProfileModel
import com.dhandev.expenseeye.data.model.TransactionItemModel
import com.dhandev.expenseeye.domain.TransactionRepository
import com.dhandev.expenseeye.utils.TransactionCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(
    private val preference: DataStorePreference,
    private val trxRepository: TransactionRepository
) : ViewModel() {
    val logged = mutableStateOf(false)
    val loading = mutableStateOf(true)
    val username = mutableStateOf("")
    val reportPeriod = mutableIntStateOf(1)
    private val incomeBalance = mutableDoubleStateOf(0.0)
    private val expenseBalance = mutableDoubleStateOf(0.0)
    val balance = incomeBalance.doubleValue - expenseBalance.doubleValue

    fun saveProfileData(data: ProfileModel) {
        viewModelScope.launch(Dispatchers.IO) {
            preference.saveProfileData(data)
            trxRepository.insertItem(TransactionItemModel(
                title = "Initial balance",
                total = data.balance.toDouble(),
                dateInMillis = System.currentTimeMillis(),
                category = TransactionCategory.Income.toString(),
                isExpense = false
            ))
        }
    }
    fun getAll(fromDataInMillis: Long) = trxRepository.getAllTransaction(fromDataInMillis).asLiveData()

    init {
        viewModelScope.launch {
            preference.getProfileData.collect { profile ->
                logged.value = profile != null
                loading.value = false
                username.value = profile?.userName ?: ""
                reportPeriod.intValue = profile?.reportPeriod ?: 1
            }

            trxRepository.getTotalIncome().collect{income ->
                incomeBalance.doubleValue = income
            }
            trxRepository.getTotalExpense().collect{expense ->
                expenseBalance.doubleValue = expense
            }
        }
    }
}