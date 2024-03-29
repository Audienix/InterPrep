package com.twain.interprep.presentation.ui.modules.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.InterviewList
import com.twain.interprep.data.model.InterviewListMetaData
import com.twain.interprep.data.model.InterviewType
import com.twain.interprep.data.model.ViewResult
import com.twain.interprep.datastore.usecase.DataStoreUseCase
import com.twain.interprep.domain.usecase.interview.InterviewUseCase
import com.twain.interprep.helper.CoroutineContextDispatcher
import com.twain.interprep.presentation.ui.modules.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    contextProvider: CoroutineContextDispatcher,
    private val interviewUseCase: InterviewUseCase,
    private val dataStoreUseCase: DataStoreUseCase
) : BaseViewModel(contextProvider) {

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
//        val message = ExceptionHandler.parse(exception)
    }

    var interviewListMetaData: ViewResult<InterviewListMetaData> by mutableStateOf(ViewResult.UnInitialized)

    var todayInterviewState: ViewResult<List<Interview>> by mutableStateOf(ViewResult.UnInitialized)
    val todayInterviews = mutableStateListOf<Interview>()
    var username by mutableStateOf("")

    var isLoading by mutableStateOf(false)

    fun getUsername() = launchCoroutineIO {
        dataStoreUseCase.usernameUseCase.getUsername().collect {
            username = it
        }
    }
    fun getTodayInterviews() = launchCoroutineIO {
        isLoading = true
        interviewUseCase.getTodayInterviews().collect {list->
            todayInterviewState = ViewResult.Loaded(list)
            todayInterviews.addAll(list)
            isLoading = false
        }
    }
    fun getInterviews() = launchCoroutineIO {
        isLoading = true
        interviewUseCase.getInterviews().collect {
            interviewListMetaData = ViewResult.Loaded(it)
            isLoading = false
        }
    }

    fun loadMore(type: InterviewType) = launchCoroutineIO {

        if (interviewListMetaData !is ViewResult.Loaded) return@launchCoroutineIO

        val currentState = (interviewListMetaData as ViewResult.Loaded<InterviewListMetaData>).data

        val originalList: InterviewList = when (type) {
            InterviewType.PAST -> currentState.pastInterviewList
            InterviewType.PRESENT -> currentState.upcomingInterviewList
            InterviewType.FUTURE -> currentState.comingNextInterviewList
        }
        if (!isLoading) {
            isLoading = true
            interviewUseCase.getInterviewList(
                type = type,
                page = originalList.page + 1,
                currentState = currentState
            ).collect {
                interviewListMetaData = ViewResult.Loaded(it)
                isLoading = false
            }
        }
    }
}
