package com.twain.interprep.data.repository

import androidx.annotation.WorkerThread
import com.twain.interprep.data.dao.NoteDAO
import com.twain.interprep.data.model.Note
import com.twain.interprep.domain.repository.NoteRepository
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(private val noteDAO: NoteDAO) : NoteRepository {

    @WorkerThread
    override suspend fun getAllInterviewWithNotes() = noteDAO.getAllInterviewNoteMap().map { it.toList() }

    @WorkerThread
    override suspend fun getInterviewByIdWithNotes(interviewId: Int) =
        noteDAO.getAllInterviewNoteMap().map { map ->
            map.entries.first { entry -> entry.key.interviewId == interviewId }.toPair()
        }

    @WorkerThread
    override suspend fun insertNote(note: Note) = noteDAO.insertNote(note).toInt()

    @WorkerThread
    override suspend fun updateNote(note: Note) { noteDAO.updateNote(note) }

}