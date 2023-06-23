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

    fun getString(pair: StringPair) = preferences.getString(pair.key, pair.default)

    fun putString(key: String, value: String){
        editor.putString(key, value).apply()
    }

    companion object {
        const val FLAG_QUOTE_INSERTED = "FLAG_QUOTE_INSERTED"
    }
}

sealed class IntPair(
    val key: String,
    val default: Int
) {
}

sealed class BooleanPair(
    val key: String,
    val default: Boolean
) {
    object QUOTE_INSERTED: BooleanPair(PrefManager.FLAG_QUOTE_INSERTED, false)
}

sealed class StringPair(
    val key: String,
    val default: String
) {}