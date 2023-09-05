package com.twain.interprep.presentation.ui.components.generic

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.twain.interprep.data.ui.TextInputAttributes

@Composable
fun IPTextInputDialog(
    @StringRes titleRes: Int,
    @StringRes cancelButtonRes: Int,
    @StringRes confirmButtonRes: Int,
    inputText: String,
    onTextUpdate: (String) -> Unit,
    textInputAttributes: TextInputAttributes,
    onCancelClick: () -> Unit,
    onConfirmClick: () -> Unit
) {
    IPCustomAlertDialog(
        titleRes = titleRes,
        onCancelClick = onCancelClick,
        onConfirmClick = onConfirmClick,
        cancelButtonRes = cancelButtonRes,
        confirmButtonRes = confirmButtonRes
    ) {
        IPTextInput(
            inputText = inputText,
            textInputAttributes = textInputAttributes,
            onTextUpdate = onTextUpdate
        )
    }
}
