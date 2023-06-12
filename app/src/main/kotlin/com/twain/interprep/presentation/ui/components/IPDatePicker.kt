package com.twain.interprep.presentation.ui.components

import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.twain.interprep.R
import com.twain.interprep.constants.StringConstants
import com.twain.interprep.presentation.ui.theme.Purple200
import com.twain.interprep.utils.DateUtils.Companion.convertDateToMilliseconds
import com.twain.interprep.utils.DateUtils.Companion.getCurrentDateAsString
import com.twain.interprep.utils.DateUtils.Companion.isValidDate
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IPDatePicker(
    modifier: Modifier = Modifier,
    selectedDateValue: String = "",
    onDatePickerDismiss: (selectedDate: String) -> Unit
) {
    // Date Formatter
    // val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
    val formatter = SimpleDateFormat(StringConstants.DT_FORMAT_MM_DD_YYYY, Locale.getDefault())
    formatter.timeZone = TimeZone.getTimeZone("UTC")

    var openDatePicker by remember { mutableStateOf(true) }
    var selectedDate by remember { mutableStateOf(selectedDateValue) }

    if (openDatePicker) {
        val datePickerState = rememberDatePickerState(
            convertDateToMilliseconds(selectedDateValue.ifEmpty { getCurrentDateAsString() })
        )

        DatePickerDialog(
            modifier = modifier,
            onDismissRequest = { openDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDatePicker = false
//                        selectedDate = Instant.ofEpochMilli(datePickerState.selectedDateMillis!!)
//                            .atOffset(ZoneOffset.UTC).toLocalDate().format(formatter)
                        val rawDate = datePickerState.selectedDateMillis?.let { Date(it) }
                        if (rawDate != null)
                            selectedDate = formatter.format(rawDate)
                        onDatePickerDismiss(selectedDate)
                    },
                ) {
                    Text(stringResource(id = R.string.dialog_positive_button))
                }
            },
            dismissButton = {
                TextButton(onClick = { openDatePicker = false }) {
                    Text(stringResource(id = R.string.dialog_negative_button))
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
//                dateValidator = { utcDateInMills ->
//                    isValidDate(utcDateInMills)
//                },
                colors = DatePickerDefaults.colors(
                    selectedDayContainerColor = Purple200,
                    selectedDayContentColor = Color.Black
                )
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun DatePickerPreview() {
    IPDatePicker(onDatePickerDismiss = {})
}
