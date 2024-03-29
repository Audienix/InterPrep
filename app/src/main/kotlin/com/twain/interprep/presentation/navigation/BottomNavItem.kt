package com.twain.interprep.presentation.navigation

import androidx.annotation.StringRes
import com.twain.interprep.R

sealed class BottomNavItem(var route: String, var icon: Int, @StringRes var title: Int) {
    object Dashboard : BottomNavItem(
        AppScreens.MainScreens.Dashboard.route, R.drawable.ic_dashboard_24, R.string.nav_item_dashboard
    )

    object Notes : BottomNavItem(
        AppScreens.MainScreens.Notes.route, R.drawable.ic_note_24, R.string.nav_item_notes
    )

    object Resources : BottomNavItem(
        AppScreens.MainScreens.Resources.route, R.drawable.ic_resource_24, R.string.nav_item_resources
    )
}