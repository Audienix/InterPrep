package com.twain.interprep.presentation.ui.components.generic

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.twain.interprep.R
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette


@Composable
fun IPMultipleChoiceAlertDialog(
    @StringRes titleRes: Int,
    @StringRes confirmButtonRes: Int,
    @StringRes cancelButtonRes: Int,
    onCancelClick: () -> Unit,
    onConfirmClick: () -> Unit,
    options: List<String>,
    selectedIndex: Int,
    onChoiceSelected: (Int) -> Unit
) {
    IPCustomAlertDialog(
        titleRes = titleRes,
        onCancelClick = onCancelClick,
        onConfirmClick = onConfirmClick,
        confirmButtonRes = confirmButtonRes,
        cancelButtonRes = cancelButtonRes
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_8dp))
        ) {
            options.forEachIndexed { index, option ->
                MultipleChoiceOption(
                    index = index,
                    selectedIndex = selectedIndex,
                    onSelected = { onChoiceSelected(index) },
                    content = option
                )
            }
        }
    }
}


@Composable
fun MultipleChoiceOption(
    index: Int,
    selectedIndex: Int,
    onSelected: () -> Unit,
    content: String
){
    Row(
        modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.dimension_4dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = content,
            color = MaterialColorPalette.onSurface,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.weight(1f))
        RadioButton(
            selected = index == selectedIndex,
            onClick = onSelected,
            colors = RadioButtonDefaults.colors(selectedColor = MaterialColorPalette.primary)
        )
    }
}
