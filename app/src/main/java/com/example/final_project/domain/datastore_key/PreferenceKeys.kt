package com.example.final_project.domain.datastore_key

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {
    val DARK_MODE = booleanPreferencesKey("dark_mode")
    val LANGUAGE = stringPreferencesKey("language")
}