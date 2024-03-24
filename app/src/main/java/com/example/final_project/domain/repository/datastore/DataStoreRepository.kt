package com.example.final_project.domain.repository.datastore

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveBoolean(key: Preferences.Key<Boolean>, value: Boolean)
    suspend fun readBoolean(key: Preferences.Key<Boolean>): Flow<Boolean>
    suspend fun saveString(key: Preferences.Key<String>, value: String)
    suspend fun readString(key: Preferences.Key<String>): Flow<String>
}