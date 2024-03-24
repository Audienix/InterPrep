package com.twain.interprep.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {
    // Boolean Preference keys
    val HAS_ONBOARDED = booleanPreferencesKey("HAS_ONBOARDED")

    // String Preference keys
    val USER_NAME = stringPreferencesKey("USER_NAME")
    val PREFERRED_LANGUAGE = stringPreferencesKey("PREFERRED_LANGUAGE")
    val PREFERRED_LANGUAGE_CODE = stringPreferencesKey("PREFERRED_LANGUAGE_CODE")

    // Integer Preference keys
    val APP_THEME = intPreferencesKey("APP_THEME")
    val NOTIFICATION_REMINDER = intPreferencesKey("NOTIFICATION_REMINDER")
}
