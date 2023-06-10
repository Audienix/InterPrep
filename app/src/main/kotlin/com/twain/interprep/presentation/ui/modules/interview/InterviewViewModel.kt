package com.twain.interprep.presentation.ui.modules.interview

import androidx.annotation.StringRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.twain.interprep.R
import com.twain.interprep.data.model.DashBoardInterviews
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.ViewResult
import com.twain.interprep.data.model.isValid
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

    var interviews: ViewResult<DashBoardInterviews> by
    mutableStateOf(ViewResult.UnInitialized)
        private set

    var isEditInterview = false

    var interviewData: Interview by mutableStateOf(Interview())

    fun updateInterviewField(@StringRes labelTextId: Int, value: String) {
        when (labelTextId) {
            R.string.hint_label_date -> {
                interviewData = interviewData.copy(
                    date = value
                )
            }

            R.string.hint_label_time -> {
                interviewData = interviewData.copy(
                    time = value
                )
            }

            R.string.hint_label_company-> {
                interviewData = interviewData.copy(
                    company = value
                )
            }

            R.string.hint_label_interview_type -> {
                interviewData = interviewData.copy(
                    interviewType = value
                )
            }

            R.string.hint_label_role -> {
                interviewData = interviewData.copy(
                    role = value
                )
            }

            R.string.hint_label_round_count -> {
                // TODO think a better way to check
                interviewData = interviewData.copy(
                    roundNum = value
                )
            }

            R.string.hint_label_job_post -> {
                interviewData = interviewData.copy(
                    jobPostLink = value
                )
            }

            R.string.hint_label_company_link -> {
                interviewData = interviewData.copy(
                    companyLink = value
                )
            }

            R.string.hint_label_interviewer -> {
                interviewData = interviewData.copy(
                    interviewer = value
                )
            }
        }
    }

    private fun insertInterview(interview: Interview) = launchCoroutineIO {
        interviewUseCase.insertInterview(interview)
    }

    fun deleteInterview(interview: Interview) = launchCoroutineIO {
        interviewUseCase.deleteInterview(interview)
    }

    private fun updateInterview(interview: Interview) = launchCoroutineIO {
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



    fun onSaveInterview(){
        if (interviewData.isValid()) {
            if (isEditInterview) {
                updateInterview(interviewData)
            } else {
                insertInterview(interviewData)
            }
        }

        isEditInterview = false
    }

    fun onDeleteInterview(){
    }
}
