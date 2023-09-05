package com.twain.interprep.presentation.ui.components.generic

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.twain.interprep.R
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette
import com.twain.interprep.presentation.ui.theme.Shapes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun IPCustomAlertDialog(
    @StringRes titleRes: Int,
    @StringRes cancelButtonRes: Int? = null,
    @StringRes confirmButtonRes: Int? = null,
    onCancelClick: () -> Unit,
    onConfirmClick: () -> Unit,
    content: @Composable () -> Unit
) {
    AlertDialog(
        onDismissRequest = {}) {
        Surface(
            shape = Shapes.extraLarge,
            color = MaterialColorPalette.surfaceContainerHigh,
            tonalElevation = dimensionResource(id = R.dimen.dimension_6dp),
        ) {
            Column(
                modifier = Modifier.padding(
                    PaddingValues(all = dimensionResource(
                        id = R.dimen.dimension_24dp)
                    )
                )
            ) {
                Text(
                    text = stringResource(id = titleRes),
                    color = MaterialColorPalette.onSurface,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimension_16dp)))
                content()
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimension_20dp)))
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    
                    cancelButtonRes?.let {
                        TextButton(
                            onClick = onCancelClick,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = MaterialColorPalette.primary
                            )) {
                            Text(text = stringResource(id = it))
                        }
                    }
                    confirmButtonRes?.let {
                        TextButton(
                            onClick = onConfirmClick,
                            colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialColorPalette.primary
                        )) {
                            Text(text = stringResource(id = it))
                        }
                    }
                }
            }
        }
    }
}
