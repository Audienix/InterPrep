package com.twain.interprep.presentation.ui.components

import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.twain.interprep.constants.StringConstants
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IPTimePicker(
    selectedTimeValue: String = "",
    onTimePickerDismiss: (selectedTime: String) -> Unit
) {

    // Time Formatter
    val formatter = SimpleDateFormat(StringConstants.DT_FORMAT_DAY_HOUR_MIN, Locale.getDefault())
    formatter.timeZone = TimeZone.getTimeZone("UTC")

    var openTimePicker by remember { mutableStateOf(true) }
    var selectedTime by remember { mutableStateOf(selectedTimeValue) }

    if (openTimePicker) {
        val timePickerState = rememberTimePickerState()

        IPTimePickerDialog(
            onCancel = { openTimePicker = false },
            onConfirm = {
                val cal = Calendar.getInstance()
                cal.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                cal.set(Calendar.MINUTE, timePickerState.minute)
                cal.isLenient = false
                selectedTime = formatter.format(cal.time)
                onTimePickerDismiss(selectedTime)
            },
            content = {
                TimeInput(
                    state = timePickerState
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
