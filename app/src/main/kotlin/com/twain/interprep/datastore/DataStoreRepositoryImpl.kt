package com.twain.interprep.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.twain.interprep.constants.StringConstants.NO_NOTIFICATION
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.twain.interprep.data.ui.ProfileSettingsData.ProfileSettings
import com.twain.interprep.data.ui.ProfileSettingsData.Language
import com.twain.interprep.data.ui.ProfileSettingsData.AppTheme

class DataStoreRepositoryImpl(private val dataStore: DataStore<Preferences>): DataStoreRepository {
    override fun getProfileSettings(): Flow<ProfileSettings> = dataStore.data.map { preferences ->
        ProfileSettings(
            userName = preferences[PreferenceKeys.USER_NAME] ?: "",
            preferredLanguage = preferences[PreferenceKeys.PREFERRED_LANGUAGE]?.let {
                Language.valueOf(it) } ?: Language.ENGLISH_US,
            appTheme = preferences[PreferenceKeys.APP_THEME]?.let { AppTheme.valueOf(it) }
                ?: AppTheme.SYSTEM,
            notificationReminder = preferences[PreferenceKeys.NOTIFICATION_REMINDER]
                ?: NO_NOTIFICATION
        )
    }

}
