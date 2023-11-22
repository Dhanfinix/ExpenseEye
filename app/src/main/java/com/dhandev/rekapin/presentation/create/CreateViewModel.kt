package com.dhandev.rekapin.presentation.create

import androidx.lifecycle.ViewModel
import com.dhandev.rekapin.data.model.TransactionItemModel
import com.dhandev.rekapin.domain.TransactionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class CreateViewModel(private val trxRepository: TransactionRepository): ViewModel() {
    // Define a coroutine scope for the ViewModel
    private val viewModelScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    // Define an insert method that calls the insert method in the DAO
    fun insert(item: TransactionItemModel) {
        viewModelScope.launch {
            trxRepository.insertItem(item)
        }
    }
    fun update(item: TransactionItemModel) {
        viewModelScope.launch {
            trxRepository.update(item)
        }
    }

    // Don't forget to cancel the coroutine scope when the ViewModel is destroyed
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}