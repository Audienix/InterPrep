package com.twain.interprep.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note): Long

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM note WHERE noteId IN (:ids)")
    fun getNotesById(ids: List<Int>): Flow<List<Note>>

    @Query("SELECT * FROM interview LEFT JOIN note ON interview.interviewId = note.interviewId")
    fun getAllInterviewNoteMap(): Flow<Map<Interview, List<Note>>>


//    @Query("SELECT * FROM interview JOIN note ON :id = note.interviewId LIMIT 1")
//    fun getSingleInterviewNoteMap(id: Int): Flow<Pair<Interview, List<Note>>>
}