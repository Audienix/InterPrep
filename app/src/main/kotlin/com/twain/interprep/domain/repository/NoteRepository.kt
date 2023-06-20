package com.twain.interprep.domain.repository

import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun getAllInterviewWithNotes(): Flow<List<Pair<Interview, List<Note>>>>

    suspend fun getInterviewByIdWithNotes(interviewId: Int): Flow<Pair<Interview, List<Note>>>

    suspend fun insertNote(note: Note): Int

    suspend fun updateNote(note: Note)
//
//    suspend fun getSingleInterviewWithNotes(interviewId: Int): Flow<Pair<Interview, List<Note>>>
}