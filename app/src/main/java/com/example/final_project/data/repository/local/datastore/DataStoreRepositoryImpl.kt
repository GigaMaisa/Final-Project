package com.example.final_project.data.repository.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.final_project.domain.repository.datastore.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(private val dataStore: DataStore<Preferences>) : DataStoreRepository {
    override suspend fun saveBoolean(key: Preferences.Key<Boolean>, value: Boolean) {
        dataStore.edit { settings ->
            settings[key] = value
        }
    }

    override suspend fun readBoolean(key: Preferences.Key<Boolean>): Flow<Boolean> {
        return dataStore.data
            .map { preferences ->
                preferences[key] ?: false
            }
    }

    override suspend fun saveString(key: Preferences.Key<String>, value: String) {
        dataStore.edit { settings ->
            settings[key] = value
        }
    }

    override suspend fun readString(key: Preferences.Key<String>): Flow<String> {
        return dataStore.data
            .map { preferences ->
                preferences[key] ?: "english"
            }
    }
}