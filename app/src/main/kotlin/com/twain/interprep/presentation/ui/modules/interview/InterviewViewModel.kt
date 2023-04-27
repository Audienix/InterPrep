package com.twain.interprep.presentation.ui.modules.interview

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.twain.interprep.data.model.Interview
import com.twain.interprep.repository.InterviewRepository
import kotlinx.coroutines.launch

// Using LiveData and caching what allWords returns has several benefits:
class InterviewViewModel(private val repository: InterviewRepository) : ViewModel()  {

    val allInterview: LiveData<List<Interview>> = repository.allInterviews.asLiveData()

//    TODO: Define get()
//    val pastInterviews:  LiveData<List<Interview>>
//    val upcomingInterviews:  LiveData<List<Interview>>
//    val comingNextInterviews:  LiveData<List<Interview>>

    fun insert(interview: Interview) = viewModelScope.launch {
        repository.insert(interview)
    }

    fun delete(interview: Interview) = viewModelScope.launch {
        repository.delete(interview)
    }

    fun update(interview: Interview) = viewModelScope.launch {
        repository.update(interview)
    }
}