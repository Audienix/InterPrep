package com.twain.interprep.presentation.ui.modules.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.twain.interprep.data.model.DashboardInterviews
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

    var interviews: ViewResult<DashboardInterviews> by mutableStateOf(ViewResult.UnInitialized)

    fun getInterviews() = launchCoroutineIO {
        interviewUseCase.getInterviews().collect {
            interviews = ViewResult.Loaded(it)
        }
    }
}
