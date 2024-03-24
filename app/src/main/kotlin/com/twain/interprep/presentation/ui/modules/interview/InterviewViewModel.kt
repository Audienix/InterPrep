package com.twain.interprep.presentation.ui.modules.interview

import android.app.Application
import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.twain.interprep.R
import com.twain.interprep.constants.NumberConstants
import com.twain.interprep.constants.StringConstants.NOTIFICATION_KEY_MESSAGE
import com.twain.interprep.constants.StringConstants.NOTIFICATION_KEY_TITLE
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.InterviewStatus
import com.twain.interprep.data.model.ViewResult
import com.twain.interprep.datastore.usecase.DataStoreUseCase
import com.twain.interprep.datastore.usecase.SetNotificationUseCase
import com.twain.interprep.domain.repository.InterviewRepository
import com.twain.interprep.domain.usecase.interview.InterviewUseCase
import com.twain.interprep.helper.CoroutineContextDispatcher
import com.twain.interprep.notification.NotificationReminderWorker
import com.twain.interprep.presentation.ui.modules.common.BaseViewModel
import com.twain.interprep.utils.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class InterviewViewModel @Inject constructor(
    contextProvider: CoroutineContextDispatcher,
    private val interviewUseCase: InterviewUseCase,
    private val dataStoreUseCase: DataStoreUseCase,
    private val repository: InterviewRepository,
) : BaseViewModel(contextProvider) {

    private val _notification = MutableStateFlow<ViewResult<Int>>(ViewResult.UnInitialized)
    val notification: StateFlow<ViewResult<Int>> = _notification

    private val _notificationTime = MutableStateFlow(0)
    val notificationTime: StateFlow<Int> = _notificationTime

    init {
        viewModelScope.launch {
            dataStoreUseCase.getNotificationUseCase().collect {
                _notification.value = ViewResult.Loaded(it)
                // Update notificationTime based on the notification
                _notificationTime.value = when (it) {
                    0 -> 15
                    1 -> 30
                    2 -> 60
                    else -> 0
                }
            }
        }
    }

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
//        val message = ExceptionHandler.parse(exception)
    }

    var interviewData: Interview by mutableStateOf(Interview())

    // TODO think a better way to check
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

            R.string.hint_label_company -> {
                interviewData = interviewData.copy(
                    company = value
                )
            }

            R.string.hint_label_meeting_link -> {
                interviewData = interviewData.copy(
                    meetingLink = value
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
                interviewData = interviewData.copy(
                    roundNum = value
                )
            }

            R.string.hint_label_job_post_link -> {
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

    fun updateInterviewStatus(status: InterviewStatus) {
        interviewData = interviewData.copy(
            interviewStatus = status
        )
        updateInterview(interviewData)
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
        interviewUseCase.getInterviewById(id).collect { interview ->
            interview.let { interviewData = it }
        }
    }

    fun deleteAllInterview() = launchCoroutineIO {
        interviewUseCase.deleteAllInterviews()
    }

    fun onSaveInterview(isEditInterview: Boolean) {
        if (isEditInterview) {
            updateInterview(interviewData)
        } else {
            insertInterview(interviewData)
        }
    }

    fun createInterviewNotification(
        title: String,
        message: String,
        reminderTimeBefore: Int,
        tag: String
    ): OneTimeWorkRequest {
        val delayInSeconds = DateUtils.calculateTimeDifferenceInSeconds(
            interviewData.date,
            interviewData.time,
            reminderTimeBefore * NumberConstants.MINUTE_IN_SECONDS
        )

        return createNotificationWorkRequest(
            title = title,
            message = message,
            timeDelayInSeconds = delayInSeconds,
            tag = tag
        )
    }

    private fun createNotificationWorkRequest(
        title: String,
        message: String,
        timeDelayInSeconds: Long,
        tag: String
    ): OneTimeWorkRequest {
        return OneTimeWorkRequestBuilder<NotificationReminderWorker>()
            .setInitialDelay(timeDelayInSeconds, TimeUnit.SECONDS)
            .addTag(tag)
            .setInputData(
                workDataOf(
                    NOTIFICATION_KEY_TITLE to title,
                    NOTIFICATION_KEY_MESSAGE to message,
                )
            )
            .build()
    }

    fun updateFutureInterviewsNotification(minutes: Int) {
        viewModelScope.launch {
            val futureInterviews = repository.getTodayInterviews()
            // cancel existing notifications for these interviews
            futureInterviews.collect { interview ->
                val tag = "interview_${interviewData.interviewId}"
//                WorkManager.getInstance(Application.appContext).cancelAllWorkByTag(tag)
            }

            // reschedule new notifications for these interviews
            futureInterviews.collect { interview ->
                val newTag = "interview_${interviewData.interviewId}"
//                val newNotification = createInterviewNotification(
//                    title = "Interview Reminder",
//                    message = context.resources.getString(
//                        R.string.notification_reminder_description,
//                        interviewData.company,
//                        minutes,
//                    ),
//                    reminderTimeBefore = NumberConstants.MINUTE_IN_SECONDS * minutes,
//                    tag = newTag
//                )
//                WorkManager.getInstance(Application.appContext).enqueue(newNotification)
            }
        }
    }
}
