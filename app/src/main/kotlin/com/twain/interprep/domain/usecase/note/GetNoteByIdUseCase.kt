package com.twain.interprep.domain.usecase.note

import com.twain.interprep.domain.repository.NoteRepository

class GetNoteByIdUseCase(private val noteRepository: NoteRepository) {

    suspend operator fun invoke(noteId: Int) = noteRepository.getNoteById(noteId)

}
