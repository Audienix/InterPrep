package com.twain.interprep.presentation.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import com.twain.interprep.R

@Composable
fun FAB(onFABClick: () -> Unit) {
    FloatingActionButton(
        onClick = onFABClick,
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.dimension_16dp)),
    ) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = "Add FAB",
        )
    }
}