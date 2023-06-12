package com.twain.interprep.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.twain.interprep.constants.StringConstants.DB_TABLE_QUOTE

@Entity(tableName = DB_TABLE_QUOTE)
data class Quote(
    @PrimaryKey(autoGenerate = true) val quoteId: Int,
    val quote: String,
    val author: String,
)
