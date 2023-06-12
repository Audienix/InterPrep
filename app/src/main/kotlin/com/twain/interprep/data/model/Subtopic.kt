package com.twain.interprep.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.twain.interprep.constants.StringConstants.DB_TABLE_SUBTOPICS

@Entity(tableName = DB_TABLE_SUBTOPICS)
data class Subtopic(
    @PrimaryKey(autoGenerate = true) val subtopicId: Int,
    val subtopic: String,
)
