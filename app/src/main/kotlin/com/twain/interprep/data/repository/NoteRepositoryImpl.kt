package com.twain.interprep.data.repository

import androidx.annotation.WorkerThread
import com.twain.interprep.data.dao.NoteDAO
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.Note
import com.twain.interprep.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(private val noteDAO: NoteDAO) : NoteRepository {

    @WorkerThread
    override suspend fun getAllPastInterviewsWithNotes() =
        noteDAO.getAllPastInterviewsNoteMap().map { list ->
            val result = mutableListOf<Pair<Interview, List<Note>>>()
            list.forEach { data ->
                result.add(data.interview to data.notes)
            }
            result
        }

    @WorkerThread
    override suspend fun getInterviewByIdWithNotes(interviewId: Int) =
        noteDAO.getAllPastInterviewsNoteMap().map { map ->
            with(map.first { entry -> entry.interview.interviewId == interviewId }) {
                interview to notes
            }
        }

    @WorkerThread
    override suspend fun insertNote(note: Note) = noteDAO.insertNote(note).toInt()

    @WorkerThread
    override suspend fun insertAllNotes(notes: List<Note>) = noteDAO.insertAllNotes(notes)

    @WorkerThread
    override suspend fun updateNote(note: Note) {
        noteDAO.updateNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        noteDAO.deleteNote(note)
    }

    override suspend fun deleteNotesForInterview(interview: Interview) {
        noteDAO.deleteInterviewNotes(interview.interviewId)
    }

    override suspend fun getNoteById(noteId: Int): Flow<Note> = noteDAO.getNoteById(noteId)
}
