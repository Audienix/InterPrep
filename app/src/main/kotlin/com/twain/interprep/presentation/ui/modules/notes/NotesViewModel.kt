package com.twain.interprep.presentation.ui.modules.notes

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.twain.interprep.R
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.Note
import com.twain.interprep.data.model.ViewResult
import com.twain.interprep.domain.usecase.interview.InterviewUseCase
import com.twain.interprep.domain.usecase.note.NoteUseCase
import com.twain.interprep.helper.CoroutineContextDispatcher
import com.twain.interprep.presentation.ui.modules.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    contextProvider: CoroutineContextDispatcher,
    private val noteUseCase: NoteUseCase,
    private val interviewUseCase: InterviewUseCase
) : BaseViewModel(contextProvider) {

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
//        val message = ExceptionHandler.parse(exception)
    }

    var interview: ViewResult<Interview> by mutableStateOf(ViewResult.UnInitialized)
    val notes = mutableStateListOf<Note>()

    var interviewNotesPair: ViewResult<List<Pair<Interview, List<Note>>>> by mutableStateOf(
        ViewResult.UnInitialized
    )

    fun getAllPastInterviewsWithNotes() = launchCoroutineIO {
        noteUseCase.getAllPastInterviewsWithNotesUseCase().collect {
            interviewNotesPair = ViewResult.Loaded(it)
        }
    }

    fun deleteNote(note: Note) {
        launchCoroutineIO {
            noteUseCase.deleteNoteUseCase(note)
        }

    }
    fun getInterviewsWithNotesByInterviewId(interviewId: Int, isEdit: Boolean) = launchCoroutineIO {
        noteUseCase.getNotesByInterviewIdUseCase(interviewId).collect { (interview, notes) ->
            this@NotesViewModel.interview = ViewResult.Loaded(interview)
            if (isEdit) {
                this@NotesViewModel.notes.clear()
                this@NotesViewModel.notes.addAll(notes.sortedBy { it.noteId })
            } else {
                this@NotesViewModel.notes.add(Note(interviewId = interviewId))
            }
        }
    }

    fun getNoteField(@StringRes resId: Int, index: Int) = notes[index].let {
        when (resId) {
            R.string.hint_label_interview_segment -> it.interviewSegment
            R.string.hint_label_topic -> it.topic
            else -> ""
        }
    }

    fun updateNoteField(@StringRes resId: Int, index: Int, value: String) {
        val note = notes[index]
        when (resId) {
            R.string.hint_label_interview_segment -> {
                notes[index] = note.copy(
                    interviewSegment = value
                )
            }

            R.string.hint_label_topic -> {
                notes[index] = note.copy(
                    topic = value
                )
            }
        }
    }

    fun updateQuestion(index: Int, questionIndex: Int, value: String) {
        val note = notes[index]

        notes[index] = note.copy(
            questions = note.questions.mapIndexed { i, question ->
                value.takeIf { i == questionIndex } ?: question
            }
        )
    }

    fun addQuestion(index: Int) {
        val note = notes[index]
        notes[index] = note.copy(
            questions = note.questions + ""
        )
    }

    fun deleteQuestion(notesIndex:Int, questionIndex: Int) {
        val note = notes[notesIndex]
        // Check if this is the last question before deletion
        // Additional check in Viewmodel for not deleting all questions
        if (note.questions.size <= 1)
            return
        // Delete the question from the existing note.
        val questions = note.questions.toMutableList()
        questions.removeAt(questionIndex)
        notes[notesIndex] = note.copy(
            questions = questions.toList()
        )
    }

    fun addNote() = launchCoroutineIO {
        if (interview !is ViewResult.Loaded) return@launchCoroutineIO

        val index = notes.size - 1
        val note = notes[index]
        if (isNoteValid(note)) {
            if (note.noteId == 0) {
                val noteId = noteUseCase.insertNoteUseCase(note)
                notes[index] = note.copy(noteId = noteId)
            } else {
                noteUseCase.updateNoteUseCase(note)
            }
        }
    }

    private fun isNoteValid(note: Note) =
        note.interviewSegment.isNotBlank() && note.questions.none { it.isBlank() }

    private fun isNoteEmpty(note: Note) =
        note.interviewSegment.isBlank() && note.questions.all { it.isBlank() } && note.topic.isBlank()


    fun addNoteEnabled() = isNoteValid(notes.last())

    fun saveNotes() {
        launchCoroutineIO {
            noteUseCase.insertAllNotesUseCase(notes.filter { isNoteValid(it) })
        }
    }

    fun areAllNotesValid(): Boolean {
        if (notes.any { !isNoteEmpty(it) && !isNoteValid(it) }) return false
        return true
    }

    fun deleteNotesForInterview(interview: Interview) {
        launchCoroutineIO {
            noteUseCase.deleteNotesForInterviewUseCase(interview)
        }
    }
    fun deleteInterview(interview: Interview) {
        launchCoroutineIO {
            interviewUseCase.deleteInterview(interview)
        }
    }
}
