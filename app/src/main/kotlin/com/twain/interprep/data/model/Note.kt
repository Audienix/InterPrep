package com.twain.interprep.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.twain.interprep.helper.Constants
import com.twain.interprep.helper.Constants.Companion.DB_TABLE_NOTES

@Entity(tableName = DB_TABLE_NOTES)
data class Note(
    @PrimaryKey(autoGenerate = true) val noteId: Int,
    val interviewId: Int,
    val interviewSegment: String,
    val topic: String?,
    val questions: List<Question>,
)

@Entity(tableName = Constants.DB_TABLE_QUESTIONS)
data class Question(
    @PrimaryKey(autoGenerate = true) val questionsId: Int,
    val question: String,
)


