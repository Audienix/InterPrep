package com.twain.interprep.presentation.ui.components.generic

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.twain.interprep.R
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.timerTask
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import android.content.Context
import androidx.compose.animation.core.*
import androidx.compose.ui.draw.alpha
import com.twain.interprep.constants.StringConstants.DT_FORMAT_MM_DD_YYYY
import com.twain.interprep.constants.StringConstants.DT_FORMAT_MM_DD_YYYY_HH_MM_A
import com.twain.interprep.notification.NotificationHelper

@Composable
fun countTimer(timeString: String) {
    var remainingTime by remember { mutableStateOf("") }
    var shouldBlink by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val timeUpMessage = stringResource(id = R.string.time_up)
    var isNotificationShown by remember { mutableStateOf(false) }
    // define animation for blinking effect when less than 1 hour remains
    val blinkAnimation = rememberInfiniteTransition(label = "")
    val animatedAlpha by blinkAnimation.animateFloat(
        initialValue = 0f, targetValue = 1f, animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1000  // Duration of one cycle (1000 ms = 1 second)
                0.0f at 0 with LinearEasing
                1.0f at 500 with LinearEasing  // Mid-point at half duration
            }, repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    fun getRemainingTime(targetTime: Long, timeUpMessage: String): String {
        val currentTime = System.currentTimeMillis()
        val diff = targetTime - currentTime
        if (diff <= 0) {
            return timeUpMessage
        }
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        if (hours < 1) {
            shouldBlink = true
            return String.format("%02dmins" + " %02ds", minutes % 60, seconds % 60)
        } else {
            shouldBlink = false
            return String.format("%02dh" + " %02dmins", hours, minutes % 60)
        }
    }

    fun parseTimeString(timeString: String): Long {
        val dateFormat = SimpleDateFormat(DT_FORMAT_MM_DD_YYYY, Locale.getDefault())
        val timeFormat = SimpleDateFormat(DT_FORMAT_MM_DD_YYYY_HH_MM_A, Locale.getDefault())
        val date = dateFormat.format(Date()) + " " + timeString
        return timeFormat.parse(date)?.time ?: 0
    }

    val targetTime = parseTimeString(timeString)

    DisposableEffect(Unit) {
        val timer = Timer()
        val timerTask = timerTask {
            // Pass 'timeUpMessage' to 'getRemainingTime'
            remainingTime = getRemainingTime(targetTime, timeUpMessage)
        }
        timer.scheduleAtFixedRate(timerTask, 0, 1000)

        onDispose {
            timer.cancel()
        }
    }

    if(remainingTime== timeUpMessage && !isNotificationShown){
        isNotificationShown = true
        showTimeUpNotification(context)
    }

    if (remainingTime != timeUpMessage) {
        Spacer(
            modifier = Modifier.width(
                dimensionResource(id = R.dimen.dimension_12dp) + dimensionResource(id = R.dimen.dimension_2dp)
            )
        )
    } else {
        Spacer(
            modifier = Modifier.width(
                dimensionResource(id = R.dimen.dimension_20dp) + dimensionResource(id = R.dimen.dimension_2dp)
            )
        )
    }

    Text(
        modifier = Modifier.alpha(if (shouldBlink) animatedAlpha else 1f),
        text = stringResource(id = R.string.remaining_time) + ": $remainingTime",
        color = MaterialColorPalette.primaryContainer,
        style = MaterialTheme.typography.bodyMedium,
    )
}

@Composable
fun showTimeUpNotification(context: Context) {
    val title = stringResource(id = R.string.timer_notification_title)
    val content = stringResource(id = R.string.timer_notification_content)
    NotificationHelper(context).createTimerNotification(
        title,
        content,
        "timer_channel_id",
        "Timer Channel",
        "Channel for timer notification",
        1
    )
}