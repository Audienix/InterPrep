package com.twain.interprep.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.twain.interprep.constants.StringConstants
import com.twain.interprep.constants.StringConstants.DB_TABLE_NOTE

@Entity(tableName = DB_TABLE_NOTE)
data class Note(
    @PrimaryKey(autoGenerate = true) val noteId: Int = 0,
    val interviewId: Int,
    val interviewSegment: String,
    val topic: String?,
    val questions: List<Question>,
)

@Entity(tableName = StringConstants.DB_TABLE_QUESTION)
data class Question(
    @PrimaryKey(autoGenerate = true) val questionsId: Int,
    val question: String,
)


