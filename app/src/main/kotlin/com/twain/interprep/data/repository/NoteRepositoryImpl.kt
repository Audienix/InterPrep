package com.twain.interprep.data.repository

import androidx.annotation.WorkerThread
import com.twain.interprep.data.dao.InterviewDAO
import com.twain.interprep.data.dao.NoteDAO
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.Note
import com.twain.interprep.domain.repository.NoteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalCoroutinesApi::class)
class NoteRepositoryImpl(private val interviewDAO: InterviewDAO, private val noteDAO: NoteDAO) :
    NoteRepository {

    @WorkerThread
    override suspend fun getAllInterviewWithNotes() =
        interviewDAO.getAllInterviews().flatMapLatest { interviews ->
            noteDAO.getAllNotes().map { notes ->
                val result = mutableListOf<Pair<Interview, List<Note>>>()

                for (interview in interviews) {
                    result.add(interview to notes.filter { interview.noteIds.contains(it.noteId) })
                }

                result
            }
        }

    @WorkerThread
    override suspend fun getInterviewByIdWithNotes(interviewId: Int) =
        interviewDAO.getInterview(interviewId).flatMapLatest { interview ->
            noteDAO.getNotesById(interview.noteIds).map { notes ->
                interview to notes
            }
        }

    override suspend fun insertNote(note: Note) = noteDAO.insertNote(note).toInt()
    override suspend fun updateNote(note: Note) {
        noteDAO.updateNote(note)
    }

}