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
import com.twain.interprep.domain.usecase.note.NoteUseCase
import com.twain.interprep.helper.CoroutineContextDispatcher
import com.twain.interprep.presentation.ui.modules.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    contextProvider: CoroutineContextDispatcher,
    private val noteUseCase: NoteUseCase
) : BaseViewModel(contextProvider) {

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
//        val message = ExceptionHandler.parse(exception)
    }

    var interview: ViewResult<Interview> by mutableStateOf(ViewResult.UnInitialized)
    val notes = mutableStateListOf<Note>()

    var interviewNotesPair: ViewResult<List<Pair<Interview, List<Note>>>> by mutableStateOf(
        ViewResult.UnInitialized
    )
        private set

    fun getNoteInterviewPairs() = launchCoroutineIO {
        noteUseCase.getNoteUseCase().collect {
            interviewNotesPair = ViewResult.Loaded(it)
        }
    }

    fun deleteNote(interview: Interview, note: Note) {
    }

    fun initAddNoteScreen(interviewId: Int, isEdit: Boolean) = launchCoroutineIO {
        noteUseCase.getNoteByInterviewIdUseCase(interviewId).collect { (interview, notes) ->
            this@NotesViewModel.interview = ViewResult.Loaded(interview)
            if (isEdit){
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

    fun addNote() = launchCoroutineIO {
        if (interview !is ViewResult.Loaded) return@launchCoroutineIO

        val index = notes.size - 1
        val note = notes[index]
        if (isNoteValid(note)) {
            if (note.noteId == 0) {
                val noteId = noteUseCase.insertNoteUseCase(note)
                notes[index] = note.copy(noteId = noteId)
            }
            else {
                noteUseCase.updateNoteUseCase(note)
            }
        }
    }

    private fun isNoteValid(note: Note) =
        note.interviewSegment.isNotBlank() && note.questions.none { it.isBlank() }

    private fun isNotEmpty(note: Note) =
        note.interviewSegment.isBlank() && note.questions.all { it.isBlank() } && note.topic.isBlank()


    fun addNoteEnabled() = isNoteValid(notes.last())

    fun onBackPressed(): Boolean {
        if (notes.any { !isNoteValid(it) && !isNotEmpty(it) }) return false
        launchCoroutineIO {
            noteUseCase.insertAllNotesUseCase(notes.filter { isNoteValid(it) })
        }
        return true
    }


}
