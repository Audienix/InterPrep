package com.twain.interprep.presentation.ui.components.generic

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LeadingIconTab
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
import com.twain.interprep.presentation.ui.theme.Purple500

data class IPDropdownItem(
    val title: String,
    val icon: ImageVector,
    val action: () -> Unit,
)
@Composable
fun IPDropdown(items:List<IPDropdownItem>) {
    val context = LocalContext.current
    var expanded = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = { expanded.value = !expanded.value }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = stringResource(id = R.string.icon_more_vert_content_description)
            )

        }
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            val onDismiss =
            items.forEach{
                DropdownMenuItem(
                    leadingIcon = {
                        Icon(
                            imageVector = it.icon,
                            tint = Purple500,
                            contentDescription = it.title
                        )
                    },
                    text = { Text(it.title) },
                    onClick = {
                        expanded.value = false
                        it.action()
                    }
                )
            }
        }

    }
}

@Preview(showBackground = true, device = Devices.PIXEL_XL)
@Composable
fun IPDropdownPreview() {
    var items = mutableListOf<IPDropdownItem>()
    items.add(IPDropdownItem(stringResource(id = R.string.menuitem_edit_note), Icons.Default.Edit, {}))
    items.add(IPDropdownItem(stringResource(id = R.string.menuitem_delete_note), Icons.Default.Delete, {}))
    InterPrepTheme {
        IPDropdown(items = items)
    }
}