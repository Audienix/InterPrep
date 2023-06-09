package com.twain.interprep.presentation.ui.components.generic

import androidx.annotation.StringRes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.twain.interprep.R

@Composable
fun IPAlertDialog(
    @StringRes titleResId: Int,
    @StringRes contentResId: Int,
    positiveButtonText: String = stringResource(id = android.R.string.ok),
    negativeButtonText: String = stringResource(id = android.R.string.cancel),
    onPositiveButtonClick: () -> Unit,
    onNegativeButtonClick: () -> Unit,
    onDismiss: () -> Unit = { }
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = stringResource(id = titleResId))
        },
        text = {
            Text(text = stringResource(id = contentResId))
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
    IPAlertDialog(
        titleResId = R.string.alert_dialog_unsaved_interview_title,
        contentResId =R.string.alert_dialog_unsaved_interview_text,
        onPositiveButtonClick = { /*TODO*/ },
        onNegativeButtonClick = { /*TODO*/ }
    )
}
