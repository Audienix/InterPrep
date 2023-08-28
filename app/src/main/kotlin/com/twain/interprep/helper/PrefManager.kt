package com.twain.interprep.helper

import android.content.SharedPreferences
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

    fun register() {
        preferences.registerOnSharedPreferenceChangeListener { sharedPreferences, s ->  }
    }

    companion object {
        const val NUM_QUOTE_INSERTED = "NUM_QUOTE_INSERTED"
    }
}

sealed class IntPair(
    val key: String,
    val default: Int
) {
    object CurrentTotalQuoteCount: IntPair(PrefManager.NUM_QUOTE_INSERTED, 0)
}
