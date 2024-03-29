package com.twain.interprep.datastore

import com.twain.interprep.data.ui.ProfileSettingsData
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun setOnboardingStatus(hasOnboarded: Boolean)
    suspend fun getOnboardingStatus(): Flow<Boolean>

    suspend fun getProfileSettings(): Flow<ProfileSettingsData.PreferenceItem>

    suspend fun getUsername() : Flow<String>
    suspend fun setUsername(username: String)

    suspend fun getAppTheme() : Flow<Int>
    suspend fun setAppTheme(appTheme: Int)

    suspend fun setLanguage(language: String, langCode: String)
    suspend fun getLanguage(): Flow<String>
}
