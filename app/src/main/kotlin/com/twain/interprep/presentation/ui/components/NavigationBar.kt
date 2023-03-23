package com.twain.interprep.presentation.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults.colors
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.twain.interprep.R

@Composable
fun NavigationBar(
    onDashBoardClicked: () -> Unit,
    onNotesClicked: () -> Unit,
    onResourcesClicked: () -> Unit
) {
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("Dashboard", "Notes", "Resources")
    val icons = listOf(R.drawable.ic_dashboard, R.drawable.ic_note, R.drawable.ic_resource)
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = icons[index]),
                        contentDescription = item
                    )
                },
                label = { Text(item) },
                colors = colors(
                    selectedIconColor = colorResource(R.color.black),
                    selectedTextColor = colorResource(R.color.black),
                    indicatorColor = colorResource(R.color.purple_800),
                    unselectedIconColor = colorResource(R.color.gray),
                    unselectedTextColor = colorResource(R.color.dark_gray),
                ),
                selected = selectedItem == index,
                onClick = { selectedItem = index }
            )
        }
    }
}
