package com.twain.interprep.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.twain.interprep.util.StringConstants.Companion.DB_TABLE_TOPICS

@Entity(tableName = DB_TABLE_TOPICS)
data class Topic(
    @PrimaryKey(autoGenerate = true) val topicId: Int,
    val topic: String,
)