package com.twain.interprep.domain.repository

import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun getInterviewWithNotes(): Flow<List<Pair<Interview, List<Note>>>>
}