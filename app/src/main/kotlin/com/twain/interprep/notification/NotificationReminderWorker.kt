package com.twain.interprep.notification

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.twain.interprep.constants.StringConstants.NOTIFICATION_KEY_MESSAGE
import com.twain.interprep.constants.StringConstants.NOTIFICATION_KEY_TITLE

class NotificationReminderWorker(val context: Context, private val params: WorkerParameters) :
    Worker(context, params) {
    override fun doWork(): Result {
        NotificationHelper(context).createNotification(
            inputData.getString(NOTIFICATION_KEY_TITLE).toString(),
            inputData.getString(NOTIFICATION_KEY_MESSAGE).toString()
        )
        return Result.success()
    }
}
