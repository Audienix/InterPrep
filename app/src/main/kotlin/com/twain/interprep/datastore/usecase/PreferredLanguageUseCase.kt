package com.twain.interprep.datastore.usecase

import com.twain.interprep.datastore.DataStoreRepository

class PreferredLanguageUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend fun getLanguage() = dataStoreRepository.getLanguage()
    suspend fun setLanguage(language: String, langCode: String) = dataStoreRepository.setLanguage(language, langCode)
}
