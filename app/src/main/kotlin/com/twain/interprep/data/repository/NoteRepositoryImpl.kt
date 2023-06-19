package com.twain.interprep.data.repository

import androidx.annotation.WorkerThread
import com.twain.interprep.data.dao.InterviewDAO
import com.twain.interprep.data.dao.NoteDAO
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.Note
import com.twain.interprep.domain.repository.NoteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(private val interviewDAO: InterviewDAO, private val noteDAO: NoteDAO): NoteRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    @WorkerThread
    override suspend fun getInterviewWithNotes(): Flow<List<Pair<Interview, List<Note>>>> =
        interviewDAO.getAllInterviews().flatMapLatest { interviews ->
            noteDAO.getAllNotes().map { notes ->
                val result = mutableListOf<Pair<Interview, List<Note>>>()

                for (interview in interviews){
                    result.add(interview to notes.filter { interview.noteIds.contains(it.noteId) })
                }

                result
            }
        }
}