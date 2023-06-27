package com.twain.interprep.data.db.converter

import androidx.room.TypeConverter

class IntegerListConverter {
    @TypeConverter
    fun fromList(list: List<Int>) = list.joinToString(",")


    @TypeConverter
    fun toList(data: String): List<Int> = emptyList<Int>().takeIf { data.isEmpty() } ?: data.split(",").map { it.toInt() }

}
