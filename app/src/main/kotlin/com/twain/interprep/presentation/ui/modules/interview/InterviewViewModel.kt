package com.twain.interprep.presentation.ui.modules.interview

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.ViewResult
import com.twain.interprep.domain.repository.InterviewRepository
import com.twain.interprep.domain.usecase.interview.GetInterviewsUseCase
import com.twain.interprep.domain.usecase.interview.InterviewUseCase
import com.twain.interprep.presentation.ui.modules.common.BaseViewModel
import com.twain.interprep.helper.CoroutineContextDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class InterviewViewModel @Inject constructor(
    contextProvider: CoroutineContextDispatcher,
    private val interviewUseCase: InterviewUseCase,
) : BaseViewModel(contextProvider) {

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
//        val message = ExceptionHandler.parse(exception)
    }

    var interviews: ViewResult<GetInterviewsUseCase.DashBoardInterviews> by
        mutableStateOf(ViewResult.UnInitialized)
        private set


    fun insertInterview(interview: Interview) = launchCoroutineIO {
        interviewUseCase.insertInterview(interview)
    }

    fun deleteInterview(interview: Interview) = launchCoroutineIO {
        interviewUseCase.deleteInterview(interview)
    }

    fun updateInterview(interview: Interview) = launchCoroutineIO {
        interviewUseCase.updateInterview(interview)
    }

    fun getInterviews() = launchCoroutineIO {
        interviewUseCase.getInterviews().collect {
            interviews = ViewResult.Loaded(it)
        }
    }

    fun getInterviewById(id: Int) = launchCoroutineIO {
        interviewUseCase.getInterviewById(id)
    }

    fun deleteAllInterview() = launchCoroutineIO {
        interviewUseCase.deleteAllInterviews()
    }
}
