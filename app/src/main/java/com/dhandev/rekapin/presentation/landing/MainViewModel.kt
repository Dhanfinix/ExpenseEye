package com.dhandev.rekapin.presentation.landing

import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dhandev.rekapin.data.local.DataStorePreference
import com.dhandev.rekapin.data.model.ProfileModel
import com.dhandev.rekapin.data.model.TransactionItemModel
import com.dhandev.rekapin.domain.TransactionRepository
import com.dhandev.rekapin.utils.DateUtil
import com.dhandev.rekapin.utils.TransactionCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainViewModel(
    private val preference: DataStorePreference,
    private val trxRepository: TransactionRepository
) : ViewModel() {
    val logged = mutableStateOf(false)
    val loading = mutableStateOf(true)
    val username = mutableStateOf("")
    val reportPeriod = mutableIntStateOf(1)
    val budget = mutableDoubleStateOf(0.0)
    val showBalance = mutableStateOf(false)
    private val _balance = MutableLiveData<Double>()
    val balance : LiveData<Double> = _balance

    private val _expense = MutableLiveData<Double>()
    val expense : LiveData<Double> = _expense
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
    fun getIncomeExpense() = viewModelScope.launch {
        _balance.postValue(trxRepository.getTotalBalance(DateUtil.fromReportPeriodDate(reportPeriod.intValue)))
    }
    fun getExpense() = viewModelScope.launch {
        _expense.postValue(trxRepository.getTotalExpense(DateUtil.fromReportPeriodDate(reportPeriod.intValue)).first())
    }

    init {
        viewModelScope.launch {
            preference.getProfileData.collect { profile ->
                logged.value = profile != null
                loading.value = false
                username.value = profile?.userName ?: ""
                reportPeriod.intValue = profile?.reportPeriod ?: 1
                budget.doubleValue = profile?.budget?.toDouble() ?: 0.0
            }
        }
    }

    fun saveShowBalance(isShown: Boolean){
        viewModelScope.launch {
            preference.saveShowBalance(isShown)
        }
    }
    fun getShowBalance(){
        viewModelScope.launch {
            preference.getShowBalance.collect{
                showBalance.value = it
            }
        }
    }
}