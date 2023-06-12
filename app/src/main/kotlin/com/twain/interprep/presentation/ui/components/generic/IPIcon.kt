package com.twain.interprep.presentation.ui.components.generic

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun DeleteIcon(tint: Color = Color.White, onDeleteIconClick: () -> Unit) {
    IPIcon(imageVector = Icons.Filled.Delete, tint = tint, onIconClick = onDeleteIconClick)
}

@Composable
fun IPIcon(imageVector: ImageVector, tint: Color, onIconClick: () -> Unit) {
    IconButton(onClick = onIconClick) {
        Icon(imageVector = imageVector, null, tint = tint)
    }
}

