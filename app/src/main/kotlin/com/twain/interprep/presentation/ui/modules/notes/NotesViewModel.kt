package com.twain.interprep.presentation.ui.modules.notes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.Note
import com.twain.interprep.data.model.ViewResult
import com.twain.interprep.domain.usecase.NoteUseCase.NoteUseCase
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

    var interviewNotesPair: ViewResult<List<Pair<Interview, List<Note>>>> by mutableStateOf(ViewResult.UnInitialized)
        private set

    fun getNoteInterviewPairs() = launchCoroutineIO {
        noteUseCase.getNoteUseCase().collect{
            interviewNotesPair = ViewResult.Loaded(it)
        }
    }



}