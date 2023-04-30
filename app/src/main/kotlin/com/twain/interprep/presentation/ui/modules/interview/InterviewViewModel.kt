package com.twain.interprep.presentation.ui.modules.interview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twain.interprep.data.model.Interview
import com.twain.interprep.domain.repository.InterviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InterviewViewModel @Inject constructor(
    private val interviewRepository: InterviewRepository,
) : ViewModel() {


//    TODO: Define get()
//    val pastInterviews:  LiveData<List<Interview>>
//    val upcomingInterviews:  LiveData<List<Interview>>
//    val comingNextInterviews:  LiveData<List<Interview>>

    fun insertInterview(interview: Interview) = viewModelScope.launch {
        interviewRepository.insertInterview(interview)
    }

    fun deleteInterview(interview: Interview) = viewModelScope.launch {
        interviewRepository.deleteInterview(interview)
    }

    fun updateInterview(interview: Interview) = viewModelScope.launch {
        interviewRepository.updateInterview(interview)
    }
}
