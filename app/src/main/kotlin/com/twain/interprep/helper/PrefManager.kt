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

    fun getBoolean(pair: BooleanPair) = preferences.getBoolean(pair.key, pair.default)

    fun putBoolean(key: String, value: Boolean){
        editor.putBoolean(key, value).apply()
    }

    companion object {
        const val NUM_QUOTE_INSERTED = "NUM_QUOTE_INSERTED"
        const val HAS_ONBOARDED = "HAS_ONBOARDED"
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
    object HasOnboarded: BooleanPair(PrefManager.HAS_ONBOARDED, false)
}
