package com.twain.interprep.data.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

object ProfileSettingsData {


    data class ProfileSettingsItemData(
        val imageVector: ImageVector? = null,
        @DrawableRes val imageRes: Int? = null,
        @StringRes val title: Int,
        val label: String,
        val clickAction: ClickAction
    )

    data class PreferenceItem(
        val userName: String = "",
        val preferredLanguage: String = "",
        val appTheme: String = "",
        val notificationReminder: String = ""
    )

    enum class ClickAction {
        NONE,
        NAME,
        PREFERRED_LANGUAGE,
        APP_THEME,
        NOTIFICATION_REMINDER,
        RATING_FEEDBACK,
        PRIVACY_POLICY
    }

}
