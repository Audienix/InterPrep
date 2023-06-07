package com.twain.interprep.presentation.ui.modules.interview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.ViewResult
import com.twain.interprep.data.ui.InterviewLabel
import com.twain.interprep.data.ui.OnEditInterview
import com.twain.interprep.data.ui.isValid
import com.twain.interprep.domain.usecase.interview.GetInterviewsUseCase
import com.twain.interprep.domain.usecase.interview.InterviewUseCase
import com.twain.interprep.helper.CoroutineContextDispatcher
import com.twain.interprep.presentation.ui.modules.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
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

    var onEditInterview: OnEditInterview by mutableStateOf(OnEditInterview())

    fun updateInterviewField(label: InterviewLabel, value: String) {
        when (label) {
            InterviewLabel.DATE -> {
                onEditInterview = onEditInterview.copy(
                    dateYMD = value
                )
            }

            InterviewLabel.TIME -> {
                onEditInterview = onEditInterview.copy(
                    dateHM = value
                )
            }

            InterviewLabel.COMPANY -> {
                onEditInterview = onEditInterview.copy(
                    company = value
                )
            }

            InterviewLabel.INTERVIEW_TYPE -> {
                onEditInterview = onEditInterview.copy(
                    interviewType = value
                )
            }

            InterviewLabel.ROLE -> {
                onEditInterview = onEditInterview.copy(
                    role = value
                )
            }

            InterviewLabel.ROUND -> {
                // TODO think a better way to check
                onEditInterview = onEditInterview.copy(
                    roundNum = value.toIntOrNull() ?: -1
                )
            }

            InterviewLabel.JOB_POST -> {
                onEditInterview = onEditInterview.copy(
                    jobPostLink = value
                )
            }

            InterviewLabel.COMPANY_LINK -> {
                onEditInterview = onEditInterview.copy(
                    companyLink = value
                )
            }

            InterviewLabel.INTERVIEWER -> {
                onEditInterview = onEditInterview.copy(
                    interviewer = value
                )
            }
        }
    }

    fun insertInterview(interview: Interview) = launchCoroutineIO {
        if (onEditInterview.isValid()) interviewUseCase.insertInterview(interview)
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
