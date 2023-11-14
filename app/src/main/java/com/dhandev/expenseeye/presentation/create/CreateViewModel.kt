package com.dhandev.expenseeye.presentation.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dhandev.expenseeye.data.local.room.TransactionDao
import com.dhandev.expenseeye.data.model.Transaction
import com.dhandev.expenseeye.domain.TransactionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class CreateViewModel(private val trxRepository: TransactionRepository): ViewModel() {
    // Define a coroutine scope for the ViewModel
    private val viewModelScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    fun getAll() = trxRepository.getAllTransaction().asLiveData()

    // Define an insert method that calls the insert method in the DAO
    fun insert(item: Transaction) {
        viewModelScope.launch {
            trxRepository.insertItem(item)
        }
    }

    // Don't forget to cancel the coroutine scope when the ViewModel is destroyed
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}