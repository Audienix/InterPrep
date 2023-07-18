package com.twain.interprep.presentation.ui.modules.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.twain.interprep.data.model.InterviewListMetaData
import com.twain.interprep.data.model.InterviewType
import com.twain.interprep.data.model.ViewResult
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
) : BaseViewModel(contextProvider) {

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
//        val message = ExceptionHandler.parse(exception)
    }

//    var interviews: ViewResult<DashboardInterviews> by mutableStateOf(ViewResult.UnInitialized)

    var interviewListMataData: ViewResult<InterviewListMetaData> by mutableStateOf(ViewResult.UnInitialized)

    var isLoading by mutableStateOf(false)

    fun getInterviews() = launchCoroutineIO {
        isLoading = true
        interviewUseCase.getInterviews().collect {
            interviewListMataData = ViewResult.Loaded(it)
            isLoading = false
        }
    }

    fun loadMore(type: InterviewType) = launchCoroutineIO {

        if (interviewListMataData !is ViewResult.Loaded) return@launchCoroutineIO

        val currentState = (interviewListMataData as ViewResult.Loaded<InterviewListMetaData>).data

        val originalList = when (type) {
            InterviewType.PAST -> currentState.pastInterviewList
            InterviewType.UPCOMING -> currentState.upcomingInterviewList
            InterviewType.COMING_NEXT -> currentState.comingNextInterviewList
        }
        if (!isLoading) {
            isLoading = true
            interviewUseCase.getTypedInterviews(
                type = type,
                page = originalList.page + 1,
                currentState = currentState
            ).collect {
                interviewListMataData = ViewResult.Loaded(it)
                isLoading = false
            }
        }
    }
}
