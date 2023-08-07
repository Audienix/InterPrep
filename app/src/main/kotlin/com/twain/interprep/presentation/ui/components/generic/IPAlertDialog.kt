package com.twain.interprep.presentation.ui.components.generic

import androidx.annotation.StringRes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.twain.interprep.R
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette

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
        containerColor = MaterialColorPalette.surfaceContainerHigh,
        titleContentColor = MaterialColorPalette.onSurface,
        textContentColor = MaterialColorPalette.onSurfaceVariant,
        confirmButton = {
            TextButton(
                onClick = onPositiveButtonClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = MaterialColorPalette.primary
                )
            ) {
                Text(text = positiveButtonText)
            }
        },
        dismissButton = {
            TextButton(
                onClick = onNegativeButtonClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = MaterialColorPalette.primary
                )
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
        contentResId = R.string.alert_dialog_unsaved_interview_text,
        onPositiveButtonClick = { /*TODO*/ },
        onNegativeButtonClick = { /*TODO*/ }
    )
}
