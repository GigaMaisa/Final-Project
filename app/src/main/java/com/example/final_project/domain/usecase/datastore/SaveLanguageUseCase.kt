package com.example.final_project.domain.usecase.datastore

import com.example.final_project.domain.datastore_key.PreferenceKeys
import com.example.final_project.domain.repository.datastore.DataStoreRepository
import javax.inject.Inject

class SaveLanguageUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository) {
    suspend operator fun invoke(language: String)  {
        dataStoreRepository.saveString(key = PreferenceKeys.LANGUAGE, value = language)
    }
}