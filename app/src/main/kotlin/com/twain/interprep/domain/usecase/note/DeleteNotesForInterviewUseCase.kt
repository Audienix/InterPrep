package com.twain.interprep.domain.usecase.note

import com.twain.interprep.data.model.Interview
import com.twain.interprep.domain.repository.NoteRepository

class DeleteNotesForInterviewUseCase(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(
        interview: Interview
    ) = noteRepository.deleteNotesForInterview(interview)

}
