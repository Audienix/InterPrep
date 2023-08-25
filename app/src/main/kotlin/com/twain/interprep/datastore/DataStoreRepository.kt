package com.twain.interprep.datastore

import com.twain.interprep.data.ui.ProfileSettingsData
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun getProfileSettings() : Flow<ProfileSettingsData.PreferenceItem>

    suspend fun setUsername(username: String)

    suspend fun getUsername() : Flow<String>
}
