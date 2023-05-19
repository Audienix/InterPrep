package com.twain.interprep.presentation.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.twain.interprep.R
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AlertDialog(
    title: String,
    content: String,
    positiveButtonText: String = stringResource(id = R.string.dialog_positive_button),
    negativeButtonText: String = stringResource(id = R.string.dialog_negative_button),
    onPositiveButtonClick: () -> Unit,
    onNegativeButtonClick: () -> Unit,
    onDismiss: () -> Unit = { }
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = title)
        },
        text = {
            Text(text = content)
        },
        confirmButton = {
            TextButton(
                onClick = onPositiveButtonClick
            ) {
                Text(text = positiveButtonText)
            }
        },
        dismissButton = {
            TextButton(
                onClick = onNegativeButtonClick
            ) {
                Text(text = negativeButtonText)
            }
        }
    )
}


@Preview(showSystemUi = true, showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun AlertDialogPreview() {
    AlertDialog(
        title = stringResource(id = R.string.alert_dialog_unsaved_interview_title),
        content = stringResource(id = R.string.alert_dialog_unsaved_interview_text),
        onPositiveButtonClick = { /*TODO*/ },
        onNegativeButtonClick = { /*TODO*/ }
    )
}

