package com.twain.interprep.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.twain.interprep.R
import com.twain.interprep.data.ui.ProfileSettingsData.PreferenceItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class DataStoreRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
    private val context: Context
) : DataStoreRepository {
    override suspend fun setOnboardingStatus(hasOnboarded: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.HAS_ONBOARDED] = hasOnboarded
        }
    }

    override suspend fun getOnboardingStatus(): Flow<Boolean> = dataStore.data.map {
        it[PreferenceKeys.HAS_ONBOARDED] ?: false
    }

    override suspend fun getProfileSettings(): Flow<PreferenceItem> =
        dataStore.data.catch { exception ->
            handleDataStoreException(exception)
        }.map { preferences ->
            PreferenceItem(
                userName = preferences[PreferenceKeys.USER_NAME] ?: "",
                preferredLanguage = preferences[PreferenceKeys.PREFERRED_LANGUAGE]
                    ?: context.resources.getStringArray(R.array.language_option)[0],
                appTheme = preferences[PreferenceKeys.APP_THEME] ?: 2,
                notificationReminder = preferences[PreferenceKeys.NOTIFICATION_REMINDER]
                    ?: context.resources.getStringArray(R.array.notification_reminder_option)[2]
            )
        }

    override suspend fun setUsername(username: String) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.USER_NAME] = username
        }
    }

    override suspend fun getUsername(): Flow<String> = dataStore.data.map {
        it[PreferenceKeys.USER_NAME] ?: ""
    }

    override suspend fun getAppTheme(): Flow<Int> = dataStore.data.map {
        it[PreferenceKeys.APP_THEME] ?: 2
    }

    override suspend fun setAppTheme(appTheme: Int) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.APP_THEME] = appTheme
        }
    }

    override suspend fun setLanguage(language: String, langCode: String) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.PREFERRED_LANGUAGE] = language
            preferences[PreferenceKeys.PREFERRED_LANGUAGE_CODE] = langCode
        }
    }

    override suspend fun getLanguage(): Flow<String> = dataStore.data.map {
        it[PreferenceKeys.PREFERRED_LANGUAGE] ?: ""
    }

    private suspend fun FlowCollector<Preferences>.handleDataStoreException(
        exception: Throwable
    ) {
        if (exception is IOException) {
            Log.e(
                DataStoreRepositoryImpl::class.qualifiedName,
                "Error reading profile setting preferences $exception"
            )
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }
}
