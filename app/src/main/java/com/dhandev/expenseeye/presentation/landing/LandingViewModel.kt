package com.dhandev.expenseeye.presentation.landing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhandev.expenseeye.data.local.DataStorePreference
import com.dhandev.expenseeye.data.model.ProfileModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LandingViewModel(private val preference: DataStorePreference) : ViewModel()  {
    fun saveProfileData(data: ProfileModel) {
        viewModelScope.launch(Dispatchers.IO) {
            preference.saveProfileData(data)
        }
    }

}