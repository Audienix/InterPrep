package com.twain.interprep.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import com.twain.interprep.constants.StringConstants
import com.twain.interprep.constants.StringConstants.DB_TABLE_NOTE

@Entity(tableName = DB_TABLE_NOTE,
    foreignKeys = [ForeignKey(
        entity = Interview::class,
        parentColumns = ["interviewId"],
        childColumns = ["interviewId"],
        onDelete = CASCADE)])
data class Note(
    @PrimaryKey(autoGenerate = true) val noteId: Int = 0,
    val interviewId: Int,
    val interviewSegment: String = "",
    val topic: String = "",
    val questions: List<String> = listOf(""),
)

@Entity(tableName = StringConstants.DB_TABLE_QUESTION)
data class Question(
    @PrimaryKey(autoGenerate = true) val questionsId: Int = 0,
    val question: String = "",
)


