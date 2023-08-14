package com.twain.interprep.domain.usecase.note

import com.twain.interprep.domain.repository.NoteRepository

class GetAllPastInterviewsWithNotesUseCase(private val noteRepository: NoteRepository) {
    suspend operator fun invoke() = noteRepository.getAllPastInterviewsWithNotes()
}
