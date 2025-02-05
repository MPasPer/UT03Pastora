package com.mapp.ut03

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsDataStore(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
        val EURO_TO_DOLLAR_RATE = doublePreferencesKey("euro_to_dollar_rate")
        val DOLLAR_TO_EURO_RATE = doublePreferencesKey("dollar_to_euro_rate")
    }

    suspend fun saveEuroToDollarRate(rate: Double) {
        context.dataStore.edit { preferences ->
            preferences[EURO_TO_DOLLAR_RATE] = rate
        }
    }

    fun getEuroToDollarRate(): Flow<Double> {
        return context.dataStore.data.map { preferences ->
            preferences[EURO_TO_DOLLAR_RATE] ?: 1.10 // Valor por defecto
        }
    }

    suspend fun saveDollarToEuroRate(rate: Double) {
        context.dataStore.edit { preferences ->
            preferences[DOLLAR_TO_EURO_RATE] = rate
        }
    }

    fun getDollarToEuroRate(): Flow<Double> {
        return context.dataStore.data.map { preferences ->
            preferences[DOLLAR_TO_EURO_RATE] ?: 0.90 // Valor por defecto
        }
    }
}