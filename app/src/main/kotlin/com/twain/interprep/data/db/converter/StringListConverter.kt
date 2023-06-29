package com.twain.interprep.data.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StringListConverter {

    private val gson = Gson()
    @TypeConverter
    fun fromString(stringList: List<String>): String {
        return gson.toJson(stringList)
    }

    @TypeConverter
    fun toString(string: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(string, type)
    }
}