package com.twain.interprep.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import androidx.room.Update
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.Note
import com.twain.interprep.utils.DateUtils
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note): Long

    @Transaction
    suspend fun insertAllNotes(notes: List<Note>) {
        notes.forEach { insertNote(it) }
    }

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM note WHERE noteId IN (:ids)")
    fun getNotesById(ids: List<Int>): Flow<List<Note>>

    @Query("SELECT * FROM NOTE WHERE noteId = :noteId")
    fun getNoteById(noteId: Int): Flow<Note>

    @Transaction
    @Query("SELECT * FROM interview WHERE (date || time) < :dateCurr ORDER BY (date || time) DESC")
    fun getAllPastInterviewsNoteMap(dateCurr: String = DateUtils.getCurrentDateTimeAsString()):
            Flow<List<InterviewWithNotes>>

    @Query("DELETE FROM note where interviewId = :interviewId")
    fun deleteInterviewNotes(interviewId: Int)

    data class InterviewWithNotes(
        @Embedded val interview: Interview,
        @Relation(
            parentColumn = "interviewId",
            entityColumn = "interviewId"
        )
        val notes: List<Note>
    )
}
