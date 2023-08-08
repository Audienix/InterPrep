package com.twain.interprep.presentation.ui.components.generic

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette

@Composable
fun DeleteIcon(tint: Color = MaterialColorPalette.onSurfaceVariant, onDeleteIconClick: () -> Unit) {
    IPIcon(imageVector = Icons.Filled.Delete, tint = tint, onIconClick = onDeleteIconClick)
}

@Composable
fun EditIcon(tint: Color = MaterialColorPalette.onSurfaceVariant, onEditIconClick: () -> Unit) {
    IPIcon(imageVector = Icons.Filled.Edit, tint = tint, onIconClick = onEditIconClick)
}

@Composable
fun IPIcon(
    imageVector: ImageVector,
    tint: Color,
    contentDescription: String = "",
    onIconClick: () -> Unit
) {
    IconButton(onClick = onIconClick) {
        Icon(imageVector = imageVector, contentDescription = contentDescription, tint = tint)
    }
}

@Composable
fun IPIcon(
    painter: Painter,
    tint: Color,
    contentDescription: String = "",
    onIconClick: () -> Unit
) {
    IconButton(onClick = onIconClick) {
        Icon(painter = painter, contentDescription = contentDescription, tint = tint)
    }
}

