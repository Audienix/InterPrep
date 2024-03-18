package com.twain.interprep.presentation.ui.components.generic

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.twain.interprep.R
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.InterviewType
import com.twain.interprep.presentation.navigation.AppScreens
import com.twain.interprep.presentation.ui.components.dashboard.NoInterviewCard
import com.twain.interprep.presentation.ui.components.dashboard.interviewMockData
import com.twain.interprep.presentation.ui.modules.interview.QuotesViewModel
import com.twain.interprep.presentation.ui.theme.InterPrepTheme
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette
import com.twain.interprep.presentation.ui.theme.Shapes
import com.twain.interprep.utils.DateUtils
import com.twain.interprep.utils.formatRoundNumAndInterviewType

import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.timerTask
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*
import kotlin.concurrent.timerTask

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.compose.animation.core.*
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.ui.draw.alpha
import com.twain.interprep.constants.StringConstants.DT_FORMAT_MM_DD_YYYY
import com.twain.interprep.constants.StringConstants.DT_FORMAT_MM_DD_YYYY_HH_MM_A
import com.twain.interprep.notification.NotificationHelper

@Composable
fun IPLargeAppBar(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    todayInterviewList: List<Interview>,
    username: String,
    isInterviewDetailsVisible: State<Boolean>,
    navController: NavHostController,
    onAvatarClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .background(color = MaterialColorPalette.surfaceContainerLow)
            .padding(vertical = dimensionResource(id = R.dimen.dimension_16dp))
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_4dp))
    ) {
        GreetingsAndProfile(title, username, subtitle, onAvatarClick)
        AnimatedVisibility(isInterviewDetailsVisible.value) {
            if (todayInterviewList.isEmpty())
                NoInterviewTodayDetails()
            else
                InterviewTodayDetails(
                    todayInterviewList = todayInterviewList,
                    navController = navController
                )
        }
    }
}

@Composable
private fun GreetingsAndProfile(
    title: String,
    username: String,
    subtitle: String,
    onAvatarClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.dimension_16dp))
            .wrapContentHeight(align = Alignment.CenterVertically),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_4dp))
    ) {
        Text(
            modifier = Modifier.weight(0.9f),
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialColorPalette.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        IPCircleTextIcon(
            modifier = Modifier
                .weight(0.1f)
                .aspectRatio(1f)
                .clickable {
                    onAvatarClick()
                },
            text = username,
            size = dimensionResource(id = R.dimen.dimension_icon_size_36),
            containerColor = MaterialColorPalette.tertiary,
            borderColor = MaterialColorPalette.onTertiary,
            textColor = MaterialColorPalette.onTertiary,
        )
    }
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.dimension_16dp)),
        text = subtitle,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialColorPalette.onSurfaceVariant
    )
}

//@Composable
//fun countTimer(timeString: String) {
//    var remainingTime by remember { mutableStateOf("") }
//    var shouldBlink by remember { mutableStateOf(false) }
//    val context = LocalContext.current
//
//    val timeUpMessage = stringResource(id = R.string.time_up)
//
//    // define animation for blinking effect when less than 1 hour remains
//    val blinkAnimation = rememberInfiniteTransition(label = "")
//    val animatedAlpha by blinkAnimation.animateFloat(
//        initialValue = 0f,
//        targetValue = 1f,
//        animationSpec = infiniteRepeatable(
//            animation = keyframes {
//                durationMillis = 1000  // Duration of one cycle (1000 ms = 1 second)
//                0.0f at 0 with LinearEasing
//                1.0f at 500 with LinearEasing  // Mid-point at half duration
//            },
//            repeatMode = RepeatMode.Reverse
//        ), label = ""
//    )
//
//    fun getRemainingTime(targetTime: Long, timeUpMessage: String): String {
//        val currentTime = System.currentTimeMillis()
//        val diff = targetTime - currentTime
//        if (diff <= 0) {
//            return timeUpMessage
//        }
//        val seconds = diff / 1000
//        val minutes = seconds / 60
//        val hours = minutes / 60
//        if (hours < 1) {
//            shouldBlink = true
//            return String.format("%02dmins" +" %02ds", minutes % 60, seconds % 60)
//        } else {
//            shouldBlink = false
//            return String.format("%02dh" +" %02dmins", hours, minutes % 60)
//        }
//    }
//
//    fun parseTimeString(timeString: String): Long {
//        val dateFormat = SimpleDateFormat(DT_FORMAT_MM_DD_YYYY, Locale.getDefault())
//        val timeFormat = SimpleDateFormat(DT_FORMAT_MM_DD_YYYY_HH_MM_A, Locale.getDefault())
//        val date = dateFormat.format(Date()) + " " + timeString
//        return timeFormat.parse(date)?.time ?: 0
//    }
//
//    val targetTime = parseTimeString(timeString)
//
//    DisposableEffect(Unit) {
//        val timer = Timer()
//        val timerTask = timerTask {
//            // Pass 'timeUpMessage' to 'getRemainingTime'
//            remainingTime = getRemainingTime(targetTime, timeUpMessage)
//        }
//        timer.scheduleAtFixedRate(timerTask, 0, 1000)
//
//        onDispose {
//            timer.cancel()
//        }
//    }
//
//    if (remainingTime != timeUpMessage) {
//        Spacer(
//            modifier = Modifier.width(
//                dimensionResource(id = R.dimen.dimension_12dp) + dimensionResource(id = R.dimen.dimension_2dp)
//            )
//        )
//    } else {
//        Spacer(
//            modifier = Modifier.width(
//                dimensionResource(id = R.dimen.dimension_20dp) +
//                    dimensionResource(id = R.dimen.dimension_2dp)
//            )
//        )
//        showTimeUpNotification(context)
//    }
//
//    Text(
//        modifier = Modifier.alpha(if (shouldBlink) animatedAlpha else 1f),
//        text = stringResource(id = R.string.remaining_time) + ": $remainingTime",
//        color = MaterialColorPalette.primaryContainer,
//        style = MaterialTheme.typography.bodyMedium,
//    )
//}
//@Composable
//fun showTimeUpNotification(context: Context) {
//    val title = stringResource(id = R.string.timer_notification_title)
//    val content = stringResource(id = R.string.timer_notification_content)
//    NotificationHelper(context).createTimerNotification(
//        title,
//        content,
//        "timer_channel_id",
//        "Timer Channel",
//        "Channel for timer notification",
//        1 // You can use a dynamic ID if needed
//    )
//}

@Composable
private fun InterviewTodayDetails(
    todayInterviewList: List<Interview>,
    navController: NavHostController

) {
    Column(
        modifier = Modifier
            .padding(top = dimensionResource(id = R.dimen.dimension_8dp))
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_8dp))
    ) {
        InterviewTodayReminder(
            message = pluralStringResource(
                R.plurals.label_interviews_today,
                todayInterviewList.size,
                todayInterviewList.size
            )
        )
        if (todayInterviewList.isNotEmpty())
            TodayInterviewPager(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.dimension_8dp)),
                interviewList = todayInterviewList,
                navController = navController
            )
    }
}

@Composable
private fun NoInterviewTodayDetails() {
    Column(
        modifier = Modifier
            .padding(top = dimensionResource(id = R.dimen.dimension_8dp))
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_8dp))
    ) {
        InterviewTodayReminder(
            message = stringResource(R.string.label_no_interviews_today)
        )
        val quotesViewModel: QuotesViewModel = hiltViewModel()
        LaunchedEffect(Unit) {
            if (quotesViewModel.currentQuote.quoteId == -1)
                quotesViewModel.getQuotes()
        }

        val quote = quotesViewModel.currentQuote
        NoInterviewCard(quote = quote)
    }
}

@Composable
private fun InterviewTodayReminder(message: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.dimension_8dp)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier
                .padding(start = dimensionResource(id = R.dimen.dimension_4dp)),
            painter = painterResource(id = R.drawable.ic_notification_important_24),
            contentDescription = "contentDescription",
            tint = MaterialColorPalette.primary
        )
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialColorPalette.onSurface
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodayInterviewPager(
    modifier: Modifier,
    interviewList: List<Interview>,
    navController: NavHostController
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { interviewList.size }
    )
    Column(
        modifier = modifier
            .padding(horizontal = dimensionResource(id = R.dimen.dimension_8dp))
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_8dp))
    ) {
        HorizontalPager(state = pagerState) { _ ->
            TodayInterviewCard(
                interview = interviewList[pagerState.currentPage],
                navController = navController
            )
        }
        if (interviewList.size > 1) {
            HorizontalPagerIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                pageCount = interviewList.size,
                pagerState = pagerState,
            )
        }
    }
}

@Composable
fun TodayInterviewCard(
    interview: Interview,
    navController: NavHostController,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                navController.navigate(
                    AppScreens.MainScreens.InterviewDetails.withArgs(
                        interview.interviewId,
                        InterviewType.PRESENT,
                    )
                ) {
                    popUpTo(AppScreens.MainScreens.Dashboard.route)
                }
            },
        colors = CardDefaults.cardColors(containerColor = MaterialColorPalette.primary),
        shape = Shapes.medium,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = dimensionResource(id = R.dimen.dimension_8dp),
                    vertical = dimensionResource(
                        id = R.dimen.dimension_4dp
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_4dp))
        ) {
            Column(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.dimension_4dp)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_reminder_clock),
                    contentDescription = null
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.dimension_8dp)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_4dp))
            ) {
                // first row
                Box(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .wrapContentWidth()
                            .align(Alignment.CenterStart)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(
                            dimensionResource(id = R.dimen.dimension_4dp)
                        )
                    ) {
                        Icon(
                            modifier = Modifier.size(dimensionResource(id = R.dimen.dimension_icon_size_16)),
                            painter = painterResource(id = R.drawable.ic_timer_24),
                            tint = MaterialColorPalette.tertiaryContainer,
                            contentDescription = null
                        )
                        Text(
                            modifier = Modifier,
                            text = DateUtils.getDisplayedTime(
                                LocalContext.current,
                                interview.time
                            ),
                            color = MaterialColorPalette.primaryContainer,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        countTimer(DateUtils.getDisplayedTime(
                            LocalContext.current,
                            interview.time
                        ))
                    }
                    if (interview.meetingLink.isNotEmpty()) {
                        IPText(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .wrapContentWidth(),
                            text = stringResource(id = R.string.label_join_here),
                            link = interview.meetingLink,
                            textColor = MaterialColorPalette.onPrimary,
                            textStyle = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }

                // second row
                if (interview.interviewType.isNotEmpty()) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = formatRoundNumAndInterviewType(interview).ifEmpty {
                            stringResource(
                                id = R.string.label_no_text_available
                            )
                        },
                        color = MaterialColorPalette.primaryContainer,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // third row
                Box(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .wrapContentWidth()
//                            .align(Alignment.CenterStart)
                            .fillMaxWidth()
                            ,
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.spacedBy(
//                            dimensionResource(id = R.dimen.dimension_4dp)
//                        )
                    ) {
                        Text(
                            text = interview.company,
                            color = MaterialColorPalette.primaryContainer,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        if (interview.meetingLink.isNotEmpty()) {
                            IPText(
                                modifier = Modifier.padding(end = dimensionResource(id = R.dimen.dimension_8dp)),
                                text = stringResource(id = R.string.label_join_here),
                                link = interview.meetingLink,
                                textColor = MaterialColorPalette.onSecondary,
                                textStyle = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    }
                }
            }
        }
    }
}


@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun IPLargeAppBarPreview() {
    InterPrepTheme {
        IPLargeAppBar(
            title = "Hello Arighna",
            subtitle = "Good Morning",
            todayInterviewList = mutableListOf(interviewMockData),
            username = "AM",
            isInterviewDetailsVisible = mutableStateOf(true),
            onAvatarClick = {},
            navController = rememberNavController()
        )
    }
}
