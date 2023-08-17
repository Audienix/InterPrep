package com.twain.interprep.helper

import android.content.SharedPreferences
import com.twain.interprep.constants.StringConstants
import javax.inject.Inject

/***
 * wrapper class for the android [SharedPreferences]
 */
class PrefManager @Inject constructor(
    private val preferences: SharedPreferences
){
    private val editor = preferences.edit()

    fun getInt(pair: IntPair) = preferences.getInt(pair.key, pair.default)

    fun putInt(key: String, value: Int){
        editor.putInt(key, value).apply()
    }

    fun getBoolean(pair: BooleanPair) = preferences.getBoolean(pair.key, pair.default)

    fun putBoolean(key: String, value: Boolean){
        editor.putBoolean(key, value).apply()
    }

    fun getString(pair: StringPair) = preferences.getString(pair.key, pair.default) ?: pair.default

    fun putString(key: String, value: String){
        editor.putString(key, value).apply()
    }

    fun register() {
        preferences.registerOnSharedPreferenceChangeListener { sharedPreferences, s ->  }
    }

    companion object {
        const val NUM_QUOTE_INSERTED = "NUM_QUOTE_INSERTED"
        const val IS_INTRO_SHOWN = "IS_INTRO_SHOWN"
        const val USER_NAME = "USER_NAME"
        const val APP_THEME = "APP_THEME"
        const val LANGUAGE  = "LANGUAGE"
        const val NOTIFICATION = "NOTIFICATION"
    }
}

sealed class IntPair(
    val key: String,
    val default: Int
) {
    object CurrentTotalQuoteCount: IntPair(PrefManager.NUM_QUOTE_INSERTED, 0)
}

sealed class BooleanPair(
    val key: String,
    val default: Boolean
) {
    object IsIntroScreenShown: BooleanPair(PrefManager.IS_INTRO_SHOWN, false)
}

sealed class StringPair(
    val key: String,
    val default: String
) {
    object UserName: StringPair(PrefManager.USER_NAME, "")
    object AppTheme: StringPair(PrefManager.APP_THEME, StringConstants.SYSTEM_NAME)
    object Language: StringPair(PrefManager.LANGUAGE, StringConstants.ENGLISH_US_CODE)
    object Notification: StringPair(PrefManager.NOTIFICATION, StringConstants.NO_NOTIFICATION)

}
