package com.twain.interprep.domain.usecase.note

import com.twain.interprep.data.model.Note
import com.twain.interprep.domain.repository.NoteRepository

class InsertNoteUseCase(private val noteRepository: NoteRepository)  {
    suspend operator fun invoke(
        note: Note
    ) = noteRepository.insertNote(note)
}