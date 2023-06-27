package com.twain.interprep.data.repository

import androidx.annotation.WorkerThread
import com.twain.interprep.data.dao.NoteDAO
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.Note
import com.twain.interprep.domain.repository.NoteRepository
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(private val noteDAO: NoteDAO) : NoteRepository {

    @WorkerThread
    override suspend fun getAllInterviewWithNotes() = noteDAO.getAllInterviewNoteMap().map {
        val result = mutableListOf<Pair<Interview, List<Note>>>()
        it.forEach {
            result.add(it.interview to it.notes)
        }
        result
    }

    @WorkerThread
    override suspend fun getInterviewByIdWithNotes(interviewId: Int) =
        noteDAO.getAllInterviewNoteMap().map { map ->
            with(map.first { entry -> entry.interview.interviewId == interviewId }){
                interview to notes
            }
        }

    @WorkerThread
    override suspend fun insertNote(note: Note) = noteDAO.insertNote(note).toInt()

    @WorkerThread
    override suspend fun updateNote(note: Note) { noteDAO.updateNote(note) }

}