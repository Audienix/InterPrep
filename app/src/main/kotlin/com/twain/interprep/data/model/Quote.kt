package com.twain.interprep.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.twain.interprep.helper.Constants.Companion.DB_TABLE_QUOTES

@Entity(tableName = DB_TABLE_QUOTES)
data class Quote(
    @PrimaryKey(autoGenerate = true) val quoteId: Int,
    val quote: String,
    val author: String,
)