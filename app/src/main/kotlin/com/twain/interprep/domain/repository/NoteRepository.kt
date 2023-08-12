package com.twain.interprep.domain.repository

import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun getAllPastInterviewsWithNotes(): Flow<List<Pair<Interview, List<Note>>>>

    suspend fun getInterviewByIdWithNotes(interviewId: Int): Flow<Pair<Interview, List<Note>>>

    suspend fun insertNote(note: Note): Int

    suspend fun insertAllNotes(notes: List<Note>)

    suspend fun updateNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun deleteNotesForInterview(interview: Interview)

    suspend fun getNoteById(noteId: Int): Flow<Note>
//
//    suspend fun getSingleInterviewWithNotes(interviewId: Int): Flow<Pair<Interview, List<Note>>>
}
