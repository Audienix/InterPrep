package com.twain.interprep.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.twain.interprep.R
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.InterviewType
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette
import java.util.Calendar

@Composable
fun getTimeOfDayGreeting(): String {
    val currentTime = Calendar.getInstance()

    return when (currentTime.get(Calendar.HOUR_OF_DAY)) {
        in 0..11 -> stringResource(R.string.time_morning)
        in 12..16 -> stringResource(R.string.time_afternoon)
        else -> stringResource(R.string.time_evening)
    }
}

@Composable
fun getNameInitials(input: String): String {
    val words = input.trim().split(" ").filter { it.isNotEmpty() }
    return when (words.size) {
        0 -> ""
        1 -> words[0].take(2).uppercase()
        else -> "${words[0].first()}${words.last().first()}".uppercase()
    }
}

@Composable
fun getFirstName(input: String): String {
    val words = input.trim().split(" ").filter { it.isNotEmpty() }
    return when (words.size) {
        0 -> ""
        else -> words[0]
    }
}

@Composable
fun getInterviewCardColorPair(type: InterviewType): Pair<Color, Color> {
    return when (type) {
        InterviewType.FUTURE ->
            MaterialColorPalette.tertiaryContainer to MaterialColorPalette.onTertiaryContainer

        InterviewType.PRESENT ->
            MaterialColorPalette.primaryContainer to MaterialColorPalette.onPrimaryContainer

        else ->
            MaterialColorPalette.surfaceContainerHighest to MaterialColorPalette.onSurface
    }
}

@Composable
fun getInterviewEmptyStateTextPair(type: InterviewType): Pair<String, String> {
    return when (type) {
        InterviewType.FUTURE ->
            stringResource(id = R.string.empty_state_title_dashboard_future) to
                    stringResource(id = R.string.empty_state_description_dashboard_future)

        InterviewType.PRESENT ->
            stringResource(id = R.string.empty_state_title_dashboard_current) to
                    stringResource(id = R.string.empty_state_description_dashboard_current)

        else ->
            stringResource(id = R.string.empty_state_title_dashboard_past) to
                    stringResource(id = R.string.empty_state_description_dashboard_past)
    }
}

/**
 * Format the roundNum and interviewType of the given interview so that when roundNum is empty,
 * only interviewType is shown and when interviewType is empty, only roundNum is shown and if
 * both are empty show "N/A".
 * The result can only be one of the following
 * "N/A"
 * "#${interview.roundNum}" or "${interview.interviewType}"
 * or "#${interview.roundNum} - ${interview.interviewType}".
 *
 * @return the formatted string
 */
@Composable
fun formatRoundNumAndInterviewType(interview: Interview): String {
    val formattedRoundNum =
        if (interview.roundNum.isNotEmpty())
            "Round ${interview.roundNum} "
        else ""
    val formattedInterviewType =
        if (interview.roundNum.isNotEmpty() && interview.interviewType.isNotEmpty())
            "- ${interview.interviewType}"
        else interview.interviewType
    return if (formattedRoundNum.isEmpty() && formattedInterviewType.isEmpty()) ""
    else
        formattedRoundNum + formattedInterviewType
}