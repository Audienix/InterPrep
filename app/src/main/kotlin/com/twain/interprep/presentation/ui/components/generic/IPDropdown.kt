package com.twain.interprep.presentation.ui.components.generic


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.twain.interprep.R
import com.twain.interprep.presentation.ui.theme.InterPrepTheme
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette

@Composable
fun IPDropdown(modifier: Modifier = Modifier, items: List<IPDropdownItem>) {
    LocalContext.current
    val expanded = remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = { expanded.value = !expanded.value }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = stringResource(id = R.string.accessibility_icon_more_vert),
                tint = MaterialColorPalette.onSurfaceVariant
            )

        }
        DropdownMenu(modifier = Modifier
            .background(color = MaterialColorPalette.surfaceContainer),
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            items.forEach {
                DropdownMenuItem(
                    leadingIcon = {
                        Icon(
                            imageVector = it.icon,
                            tint = MaterialColorPalette.onSurfaceVariant,
                            contentDescription = it.title
                        )
                    },
                    text = {
                        Text(
                            text = it.title,
                            color = MaterialColorPalette.onSurface,
                            style = MaterialTheme.typography.labelLarge
                        )
                    },
                    onClick = {
                        expanded.value = false
                        it.action()
                    }
                )
            }
        }

    }
}

data class IPDropdownItem(
    val title: String,
    val icon: ImageVector,
    val action: () -> Unit,
)

@Preview(showBackground = true, device = Devices.PIXEL_XL)
@Composable
fun IPDropdownPreview() {
    val items = mutableListOf<IPDropdownItem>()
    items.add(
        IPDropdownItem(
            stringResource(id = R.string.menuitem_edit_note),
            Icons.Default.Edit
        ) {})
    items.add(
        IPDropdownItem(
            stringResource(id = R.string.menuitem_delete_note),
            Icons.Default.Delete
        ) {})
    InterPrepTheme {
        IPDropdown(items = items)
    }
}
