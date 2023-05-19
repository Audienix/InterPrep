package com.twain.interprep.presentation.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
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
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(
    modifier: Modifier,
    value: String = ""
) {
    // Date Formatter
    val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")

    val openDatePicker = remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(value) }

    OutlinedTextField(
        modifier = modifier
            .clickable { openDatePicker.value = true },
        value = selectedDate,
        onValueChange = { },
        readOnly = true,
        label = { Text(stringResource(id = R.string.hint_label_date) + " *") },
        enabled = false,
        supportingText = { Text(stringResource(id = R.string.date_picker_supporting_text)) }
    )

    if (openDatePicker.value) {
        val datePickerState = rememberDatePickerState(
            /* TODO: implement SelectedDates when it gets added to Material 3 */
        )

        DatePickerDialog(
            onDismissRequest = { openDatePicker.value = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDatePicker.value = false
                        selectedDate = Instant.ofEpochMilli(datePickerState.selectedDateMillis!!)
                            .atOffset(ZoneOffset.UTC).toLocalDate().format(formatter)
                    },
                ) {
                    Text(stringResource(id = R.string.dialog_positive_button))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { openDatePicker.value = false }
                ) {
                    Text(stringResource(id = R.string.dialog_negative_button))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showSystemUi = true, showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun DatePickerPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.dimension_16dp)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.dimension_16dp)),
    ) {
        DatePicker(modifier = Modifier.fillMaxWidth())
    }
}
