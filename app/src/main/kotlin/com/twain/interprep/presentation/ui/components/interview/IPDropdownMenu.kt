package com.twain.interprep.presentation.ui.components.interview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.PopupProperties
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette

@Composable
fun IPDropdownMenu(
    modifier: Modifier = Modifier,
    options: List<String>,
    textFieldSize: Size = Size.Zero,
    onDropdownDismiss: (selectedValue: String) -> Unit
) {
    var expanded by remember { mutableStateOf(true) }
    var selectedText by remember { mutableStateOf("") }

    Column {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            properties = PopupProperties(clippingEnabled = false),
            modifier = modifier
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                .background(color = MaterialColorPalette.surfaceContainer)
        ) {
            options.forEach { label ->
                DropdownMenuItem(
                    colors = MenuDefaults.itemColors(textColor = MaterialColorPalette.onSurface),
                    onClick = {
                        selectedText = label
                        expanded = !expanded
                        onDropdownDismiss(selectedText)
                    },
                    text = {
                        Text(text = label, style = MaterialTheme.typography.labelLarge)
                    }
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun DropdownMenuInputPreview() {
    val options = listOf("Recruiter", "Hiring Manager", "Technical", "Behavioral")

    IPDropdownMenu(
        modifier = Modifier.fillMaxWidth(),
        options = options,
        textFieldSize = Size.Zero,
        onDropdownDismiss = {}
    )
}
