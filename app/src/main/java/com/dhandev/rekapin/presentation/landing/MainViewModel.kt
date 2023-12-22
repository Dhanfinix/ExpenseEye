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
import com.dhandev.rekapin.data.model.FilterModel
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
    val target = mutableDoubleStateOf(0.0)
//    val balance = mutableDoubleStateOf(0.0)
    var userData : ProfileModel? = null
//    val showBalance = mutableStateOf(false)
    private val _showBalance = MutableLiveData(true)
    val showBalance : LiveData<Boolean> = _showBalance

    private val _isDark = MutableLiveData(false)
    val isDark : LiveData<Boolean> = _isDark

    private val _filter = MutableLiveData(DateUtil.fromDateInMillisToday)
    val filter : LiveData<Long> = _filter
    private val _filterIndex = MutableLiveData(0)
    val filterIndex = _filterIndex

    private val _incomeOutcome = MutableLiveData<Double>()
    val incomeOutcome : LiveData<Double> = _incomeOutcome

    private val _expense = MutableLiveData<Double>()
    val expense : LiveData<Double> = _expense
    private val _balance = MutableLiveData<Double>()
    val balance : LiveData<Double> = _balance
    fun saveProfileData(data: ProfileModel, isEdit: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            preference.saveProfileData(data)
            if (!isEdit){
                trxRepository.insertItem(TransactionItemModel(
                    title = "Initial balance",
                    total = data.balance,
                    dateInMillis = System.currentTimeMillis(),
                    category = TransactionCategory.Income.toString(),
                    isExpense = false
                ))
            }

        }
    }
    fun updateInitialBalance(newBalance: Double){
        viewModelScope.launch (Dispatchers.IO){
            trxRepository.update(TransactionItemModel(
                id = 1,
                title = "Initial balance",
                total = newBalance,
                dateInMillis = System.currentTimeMillis(),
                category = TransactionCategory.Income.toString(),
                isExpense = false
            ))
        }
    }
    fun getAll(fromDataInMillis: Long) = trxRepository.getAllTransaction(fromDataInMillis).asLiveData()
    fun getIncomeExpense() = viewModelScope.launch {
//        val result = trxRepository.getTotalIncomeOutcome(DateUtil.fromReportPeriodDate(reportPeriod.intValue))
//        _incomeOutcome.postValue(result)
//        preference.getProfileData.first().let{
//            _balance.value = it?.balance?.plus(result) ?: 0.0
//        }
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
                budget.doubleValue = profile?.budget ?: 0.0
                target.doubleValue = profile?.target ?: 0.0
                _balance.value = profile?.balance ?: 0.0
                userData = profile
            }
            getFilter()
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
                _showBalance.value = it
            }
        }
    }

    fun saveTheme(isDark: Boolean){
        viewModelScope.launch {
            preference.saveTheme(isDark)
        }
    }
    fun getTheme(){
        viewModelScope.launch {
            preference.getTheme.collect{
                _isDark.value = it
            }
        }
    }

    fun deleteItem(item: TransactionItemModel){
        viewModelScope.launch {
            var currentData : ProfileModel? = null
            preference.getProfileData.first().let{
                currentData = it
            }
            currentData?.balance = currentData?.balance?.plus(item.total * -1)!!
            preference.saveProfileData(currentData!!)
            trxRepository.delete(item)
        }
    }
    fun updateItem(item: TransactionItemModel){
        viewModelScope.launch {
            trxRepository.update(item)
        }
    }
    fun saveFilter(data: FilterModel){
        viewModelScope.launch {
            preference.saveFilter(data)
        }.invokeOnCompletion {
            getFilter()
        }
    }
    fun getFilter(){
        viewModelScope.launch {
            preference.getFilter.collect{
                _filter.value = it?.filterInMillis ?: DateUtil.fromDateInMillisToday
                _filterIndex.value = it?.index ?: 0
            }
        }
    }
    fun logout(){
        viewModelScope.launch(Dispatchers.IO) {
            trxRepository.clear()
            preference.clear()
        }
    }
}