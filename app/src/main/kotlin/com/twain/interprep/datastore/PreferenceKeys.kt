package com.twain.interprep.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {
    // Boolean Preference keys
    val HAS_ONBOARDED = booleanPreferencesKey("HAS_ONBOARDED")

    // String Preference keys
    val USER_NAME = stringPreferencesKey("USER_NAME")
    val PREFERRED_LANGUAGE = stringPreferencesKey("PREFERRED_LANGUAGE")
    val APP_THEME = stringPreferencesKey("APP_THEME")
    val NOTIFICATION_REMINDER = stringPreferencesKey("NOTIFICATION_REMINDER")

    // Integer Preference keys
}
