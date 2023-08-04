package com.twain.interprep.presentation.ui.components.generic

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import com.twain.interprep.R
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette

@Composable
fun IPFAB(onFABClick: () -> Unit) {
    FloatingActionButton(
        onClick = onFABClick,
        containerColor = MaterialColorPalette.primary,
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.dimension_16dp)),
    ) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = "Add FAB",
            tint = MaterialColorPalette.onPrimary
        )
    }
}
