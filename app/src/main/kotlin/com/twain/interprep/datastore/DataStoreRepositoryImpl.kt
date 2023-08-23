package com.twain.interprep.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.twain.interprep.R
import com.twain.interprep.data.ui.ProfileSettingsData.PreferenceItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
    private val context: Context
) : DataStoreRepository {
    override fun getProfileSettings(): Flow<PreferenceItem> = dataStore.data.map { preferences ->
        PreferenceItem(
            userName = preferences[PreferenceKeys.USER_NAME] ?: "",
            preferredLanguage = preferences[PreferenceKeys.PREFERRED_LANGUAGE]
                ?: context.resources.getStringArray(R.array.language_option)[0],
            appTheme = preferences[PreferenceKeys.APP_THEME]
                ?: context.resources.getStringArray(R.array.theme_option)[2],
            notificationReminder = preferences[PreferenceKeys.NOTIFICATION_REMINDER]
                ?: context.resources.getStringArray(R.array.notification_reminder_option)[3]
        )
    }

}
