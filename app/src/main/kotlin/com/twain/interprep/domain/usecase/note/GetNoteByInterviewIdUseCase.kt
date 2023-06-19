package com.twain.interprep.domain.usecase.note

import com.twain.interprep.domain.repository.NoteRepository

class GetNoteByInterviewIdUseCase(private val noteRepository: NoteRepository) {

    suspend operator fun invoke(interviewId: Int) =
        noteRepository.getInterviewByIdWithNotes(interviewId)
}