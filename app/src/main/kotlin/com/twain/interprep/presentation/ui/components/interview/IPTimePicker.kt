package com.twain.interprep.presentation.ui.components.interview

import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.twain.interprep.constants.StringConstants
import com.twain.interprep.presentation.ui.theme.BackgroundLightPurple
import com.twain.interprep.presentation.ui.theme.BackgroundSurface
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IPTimePicker(
    selectedTimeValue: String = "",
    onTimePickerDismiss: (selectedTime: String) -> Unit
) {
    // Time Formatter
    val formatter = SimpleDateFormat(StringConstants.DT_FORMAT_HOUR_MIN, Locale.getDefault())
    formatter.timeZone = TimeZone.getTimeZone("UTC")

    var openTimePicker by remember { mutableStateOf(true) }
    var selectedTime by remember { mutableStateOf(selectedTimeValue) }

    if (openTimePicker) {
        var initialHour = 0
        var initialMinute = 0

        // Initial selected time value parsing
        if (selectedTimeValue.isNotEmpty()) {
            val parsedInitialTime = selectedTimeValue.split(":")
            initialHour = parsedInitialTime[0].toInt()
            initialMinute = parsedInitialTime[1].toInt()
        }

        val timePickerState = rememberTimePickerState(
            initialHour = initialHour,
            initialMinute = initialMinute,
        )

        IPTimePickerDialog(
            onCancel = { openTimePicker = false },
            onConfirm = {
                openTimePicker = false
                val cal = Calendar.getInstance(java.util.TimeZone.getTimeZone("UTC"), Locale.getDefault())
                cal.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                cal.set(Calendar.MINUTE, timePickerState.minute)
                cal.isLenient = false
                selectedTime = formatter.format(cal.time)
                onTimePickerDismiss(selectedTime)
            },
            content = {
                TimePicker(
                    state = timePickerState,
                    colors = TimePickerDefaults.colors(
                        clockDialSelectedContentColor = Color.White,
                        clockDialColor = BackgroundLightPurple,
                        timeSelectorSelectedContainerColor = BackgroundSurface,
                        timeSelectorUnselectedContainerColor = Color.Transparent
                    )
                )
            }
        )
    }
}

@Preview(showSystemUi = true, showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun TimePickerPreview() {
    IPTimePicker(onTimePickerDismiss = {})
}
