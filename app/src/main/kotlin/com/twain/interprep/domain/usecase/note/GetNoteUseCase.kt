package com.twain.interprep.domain.usecase.note

import android.icu.text.SimpleDateFormat
import com.twain.interprep.constants.StringConstants
import com.twain.interprep.domain.repository.NoteRepository
import com.twain.interprep.utils.DateUtils
import kotlinx.coroutines.flow.map
import java.util.Date
import java.util.Locale

class GetNoteUseCase(private val noteRepository: NoteRepository) {
    suspend operator fun invoke() =
        noteRepository.getAllInterviewWithNotes().map { interviewList ->
            interviewList.filter { (interview, _) ->
                val date = DateUtils.convertDateTimeStringToDate(interview.date, interview.time)
                date.before(Date())
            }
            // Sort the list by date & time
            val dateFormat =
                SimpleDateFormat(StringConstants.DT_FORMAT_MM_DD_YYYY_HH_MM, Locale.getDefault())
            interviewList.sortedWith(compareBy {
                dateFormat.parse(
                    "${it.first.date} ${it.first.time}"
                )
            }).toMutableList()
        }
}
