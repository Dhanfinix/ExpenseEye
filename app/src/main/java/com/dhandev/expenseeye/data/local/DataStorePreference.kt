package com.dhandev.expenseeye.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.dhandev.expenseeye.data.model.ProfileModel
import com.google.gson.Gson
import kotlinx.coroutines.flow.map

private var THEME = booleanPreferencesKey("theme")
private var USER_INFO = stringPreferencesKey("user_info")
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "profile")

class DataStorePreference (context: Context){
    private val dataStore = context.dataStore

    suspend fun saveTheme(isDark:Boolean){
        dataStore.edit {preference->
            preference[THEME] = isDark
        }
    }

    val getTheme =dataStore.data.map {preference->
        preference[THEME] ?: false
    }

    suspend fun saveProfileData(data: ProfileModel){
        val json = Gson().toJson(data)
        dataStore.edit {preference->
            preference[USER_INFO] = json
        }
    }

    val getProfileData =dataStore.data.map {preference->
        val json = preference[USER_INFO]
        if (json != null) {
            Gson().fromJson(json, ProfileModel::class.java)
        } else {
            null
        }
    }
}