package com.twain.interprep.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.twain.interprep.R
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
    val words = input.trim().split(" ")
    return when (words.size) {
        0 -> ""
        1 -> words[0].take(2).uppercase()
        else -> "${words[0].first()}${words[1].first()}".uppercase()
    }
}
