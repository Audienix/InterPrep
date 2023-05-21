package com.twain.interprep.presentation.ui.components

import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.twain.interprep.R
import com.twain.interprep.helper.Constants
import com.twain.interprep.utils.DateUtils.Companion.convertDateToMilliseconds
import com.twain.interprep.utils.DateUtils.Companion.getCurrentDateAsString
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(
    modifier: Modifier,
    selectedDateValue: String = "",
    onDatePickerDismiss: (selectedDate: String) -> Unit,
) {
    // Date Formatter
    // val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
    val formatter = SimpleDateFormat(Constants.DT_FORMAT_MM_DD_YYYY, Locale.getDefault())
    formatter.timeZone = TimeZone.getTimeZone("UTC")

    var openDatePicker by remember { mutableStateOf(true) }
    var selectedDate by remember { mutableStateOf(selectedDateValue) }

    if (openDatePicker) {
        val datePickerState = rememberDatePickerState(
            convertDateToMilliseconds(selectedDateValue.ifEmpty { getCurrentDateAsString()})
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
            DatePicker(state = datePickerState)
        }
    }
}

@Preview(showSystemUi = true, showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun DatePickerPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.dimension_16dp)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.dimension_16dp)),
    ) {
        DatePicker(modifier = Modifier.fillMaxWidth(), onDatePickerDismiss = {})
    }
}
