package com.dhandev.expenseeye.presentation.landing

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhandev.expenseeye.data.local.DataStorePreference
import com.dhandev.expenseeye.data.model.ProfileModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val preference: DataStorePreference) : ViewModel() {
    val logged = mutableStateOf(false)
    val loading = mutableStateOf(true)
    val username = mutableStateOf("")
    val reportPeriod = mutableIntStateOf(1)

    fun saveProfileData(data: ProfileModel) {
        viewModelScope.launch(Dispatchers.IO) {
            preference.saveProfileData(data)
        }
    }

    init {
        viewModelScope.launch {
            preference.getProfileData.collect { profile ->
                logged.value = profile != null
                loading.value = false
                username.value = profile?.userName ?: ""
                reportPeriod.intValue = profile?.reportPeriod ?: 1
            }
        }
    }
}