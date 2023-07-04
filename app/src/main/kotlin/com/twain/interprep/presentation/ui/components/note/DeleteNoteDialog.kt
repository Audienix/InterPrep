package com.twain.interprep.presentation.ui.components.note

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.twain.interprep.R
import com.twain.interprep.presentation.ui.components.generic.IPAlertDialog

@Composable
fun ShowDeleteConfirmationDialog(
    showDeleteDialog: MutableState<Boolean>,
    deleteNote: () -> Unit
) {
    if (showDeleteDialog.value) {
        IPAlertDialog(
            titleResId = R.string.alert_dialog_delete_note_title,
            contentResId = R.string.alert_dialog_delete_note_text,
            onPositiveButtonClick = {
                showDeleteDialog.value = false
                deleteNote()
            }, // "OK" is clicked
            onNegativeButtonClick = {
                showDeleteDialog.value = false
            } // "CANCEL" is clicked
        )
    }
}
