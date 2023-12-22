package com.dhandev.rekapin.presentation.create

import androidx.lifecycle.ViewModel
import com.dhandev.rekapin.data.local.DataStorePreference
import com.dhandev.rekapin.data.model.ProfileModel
import com.dhandev.rekapin.data.model.TransactionItemModel
import com.dhandev.rekapin.domain.TransactionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CreateViewModel(
    private val preference: DataStorePreference,
    private val trxRepository: TransactionRepository
): ViewModel() {
    // Define a coroutine scope for the ViewModel
    private val viewModelScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    // Define an insert method that calls the insert method in the DAO
    fun insert(item: TransactionItemModel) {
        viewModelScope.launch {
            var currentData : ProfileModel? = null
            preference.getProfileData.first().let{
                currentData = it
            }
            currentData?.balance = currentData?.balance?.plus(item.total * if (item.isExpense) -1 else 1)!!
            preference.saveProfileData(currentData!!)
            trxRepository.insertItem(item)
        }
    }
    fun insertAll(items: List<TransactionItemModel>) {
        viewModelScope.launch {
            trxRepository.insertAllItem(items)
        }
    }
    fun update(item: TransactionItemModel) {
        viewModelScope.launch {
            var currentData : ProfileModel? = null
            preference.getProfileData.first().let {
                currentData = it
            }
            currentData?.balance = item.total

            preference.saveProfileData(currentData!!)

            trxRepository.update(item)
        }
    }

    // Don't forget to cancel the coroutine scope when the ViewModel is destroyed
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}