package com.twain.interprep.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {

    val USER_NAME = stringPreferencesKey("USER_NAME")

    val PREFERRED_LANGUAGE = stringPreferencesKey("PREFERRED_LANGUAGE")

    val APP_THEME = stringPreferencesKey("APP_THEME")

    val NOTIFICATION_REMINDER = stringPreferencesKey("NOTIFICATION_REMINDER")

}
