package com.twain.interprep.domain.usecase.note

import com.twain.interprep.domain.repository.NoteRepository
import com.twain.interprep.utils.DateUtils
import kotlinx.coroutines.flow.map
import java.util.Date

class GetNoteUseCase(private val noteRepository: NoteRepository) {

    suspend operator fun invoke() =
        noteRepository.getAllInterviewWithNotes().map {
            it.filter { (interview, _) ->
                val date = DateUtils.convertDateTimeStringToDate(interview.date, interview.time)
                date.before(Date())
            }
        }
}