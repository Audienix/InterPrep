package com.twain.interprep.presentation.ui.modules.interview

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.twain.interprep.R
import com.twain.interprep.data.model.DropDownOption
import com.twain.interprep.data.model.DropDownOptionType.INTERVIEW_TYPE
import com.twain.interprep.data.model.DropDownOptionType.ROLE
import com.twain.interprep.data.model.Interview
import com.twain.interprep.domain.usecase.dropDownOption.DropDownOptionUseCase
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
    private val dropDownUseCase: DropDownOptionUseCase
) : BaseViewModel(contextProvider) {

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
//        val message = ExceptionHandler.parse(exception)
    }

    var interviewData: Interview by mutableStateOf(Interview())

    var addInterviewDropDownLists: AddInterviewDropDown by mutableStateOf(AddInterviewDropDown())

    data class AddInterviewDropDown(
        val roleDropDownList: MutableList<String> = mutableListOf(),
        val interviewTypeDropDownList: MutableList<String> = mutableListOf()
    )

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
        interviewUseCase.deleteInterview(interview.copy())
    }

    private fun updateInterview(interview: Interview) = launchCoroutineIO {
        interviewUseCase.updateInterview(interview)
    }

    fun getInterviewById(id: Int) = launchCoroutineIO {
        interviewUseCase.getInterviewById(id).collect{interview ->
            interview.let { interviewData = it }
        }
    }

    fun deleteAllInterview() = launchCoroutineIO {
        interviewUseCase.deleteAllInterviews()
    }

    fun onSaveInterview(isEditInterview: Boolean){
//        if (interviewData.isValid()) {
            if (isEditInterview) {
                updateInterview(interviewData)
            } else {
                insertInterview(interviewData)
            }
//        }
    }

    fun getDropDownValues() {
        launchCoroutineIO {
            dropDownUseCase.getDropDownOptions(INTERVIEW_TYPE).collect { options ->
                addInterviewDropDownLists = addInterviewDropDownLists.copy(
                    roleDropDownList = (mutableListOf(
                        "Software Engineer"
                    ) + options).toMutableList()
                )
            }
        }
        launchCoroutineIO {
            dropDownUseCase.getDropDownOptions(ROLE).collect { options ->
                addInterviewDropDownLists = addInterviewDropDownLists.copy(
                    interviewTypeDropDownList = (mutableListOf(
                        "Technical"
                    ) + options).toMutableList()
                )
            }
        }
    }



    fun getDropDown(labelTextId: Int) =
        when (labelTextId) {
            R.string.hint_label_interview_type -> addInterviewDropDownLists.interviewTypeDropDownList
            R.string.hint_label_role -> addInterviewDropDownLists.roleDropDownList
            else -> null
        }

    fun insertDropDownOption(dropDownOption: DropDownOption) = launchCoroutineIO {
        dropDownUseCase.insertDropDownOption(dropDownOption)
    }
}
