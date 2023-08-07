package com.twain.interprep.presentation.ui.components.interview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.twain.interprep.R
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette

@Composable
fun IPTimePickerDialog(
    title: String = stringResource(id = R.string.time_picker_title),
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    toggle: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            shape = MaterialTheme.shapes.large,
            tonalElevation = dimensionResource(id = R.dimen.dimension_8dp),
            color = MaterialColorPalette.surfaceContainerHigh,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
        ) {
            toggle()
            Column(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.dimension_24dp)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = title,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialColorPalette.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimension_16dp)))
                content()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = onCancel,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialColorPalette.primary
                        )
                    ) { Text(stringResource(id = android.R.string.cancel)) }
                    TextButton(
                        onClick = onConfirm,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialColorPalette.primary
                        )
                    ) { Text(stringResource(id = android.R.string.ok)) }
                }
            }
        }
    }
}
