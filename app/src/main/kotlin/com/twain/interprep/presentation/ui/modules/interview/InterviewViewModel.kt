package com.twain.interprep.presentation.ui.modules.interview

import com.twain.interprep.data.model.Interview
import com.twain.interprep.domain.repository.InterviewRepository
import com.twain.interprep.presentation.ui.modules.common.BaseViewModel
import com.twain.interprep.helper.CoroutineContextDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

@HiltViewModel
class InterviewViewModel @Inject constructor(
    contextProvider: CoroutineContextDispatcher,
    private val interviewRepository: InterviewRepository
) : BaseViewModel(contextProvider) {

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
//        val message = ExceptionHandler.parse(exception)
    }
//    TODO: Define get()
//    val pastInterviews:  LiveData<List<Interview>>
//    val upcomingInterviews:  LiveData<List<Interview>>
//    val comingNextInterviews:  LiveData<List<Interview>>

    fun insertInterview(interview: Interview) = launchCoroutineIO {
        interviewRepository.insertInterview(interview)
    }

    fun deleteInterview(interview: Interview) = launchCoroutineIO {
        interviewRepository.deleteInterview(interview)
    }

    fun updateInterview(interview: Interview) = launchCoroutineIO {
        interviewRepository.updateInterview(interview)
    }
}
