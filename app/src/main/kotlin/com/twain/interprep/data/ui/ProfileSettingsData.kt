package com.twain.interprep.data.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.twain.interprep.R
import com.twain.interprep.constants.StringConstants

object ProfileSettingsData {
    fun getPreferenceItemDataList() =
        listOf(
            PreferenceItemData(
                imageVector = Icons.Filled.Person,
                title = R.string.label_setting_name,
                clickAction = ClickAction.NAME
            ),
            PreferenceItemData(
                imageRes = R.drawable.ic_preferred_language,
                title = R.string.label_setting_language,
                clickAction = ClickAction.PREFERRED_LANGUAGE
            ),
            PreferenceItemData(
                imageRes = R.drawable.ic_app_theme,
                title = R.string.label_setting_theme,
                clickAction = ClickAction.APP_THEME
            ),
            PreferenceItemData(
                imageVector = Icons.Filled.Notifications,
                title = R.string.label_setting_notification,
                clickAction = ClickAction.NOTIFICATION_REMINDER
            ),
            PreferenceItemData(
                imageVector = Icons.Filled.Star,
                title = R.string.label_setting_rating,
                label = R.string.label_setting_rating_description,
                clickAction = ClickAction.RATING_FEEDBACK
            ),
            PreferenceItemData(
                imageRes = R.drawable.ic_privacy_policy,
                title = R.string.label_setting_privacy_policy,
                label = R.string.label_setting_privacy_policy_description,
                clickAction = ClickAction.PRIVACY_POLICY
            )
        )

    data class PreferenceItemData(
        val imageVector: ImageVector? = null,
        @DrawableRes val imageRes: Int? = null,
        @StringRes val title: Int,
        @StringRes val label: Int? = null,
        val clickAction: ClickAction
    )

    data class ProfileSettings(
        val userName: String,
        val preferredLanguage: Language,
        val appTheme: AppTheme,
        val notificationReminder: String
    )

    enum class Language(
        val code: String,
        val dropDownName: String,
        val label: String) {
        ENGLISH_US(
            StringConstants.ENGLISH_US_CODE, StringConstants.ENGLISH_US_DROPDOWN_NAME,
            StringConstants.ENGLISH_US_LABEL)
    }

    enum class AppTheme(
        val dropDownName: String,
        val label: String) {
        SYSTEM(StringConstants.SYSTEM_NAME, StringConstants.SYSTEM_LABEL),
        DARK(StringConstants.DARK_MODE_NAME, StringConstants.DARK_MODE_LABEL),
        LIGHT(StringConstants.LIGHT_MODE_NAME, StringConstants.LIGHT_MODE_LABEL)
    }

    enum class ClickAction {
        NAME,
        PREFERRED_LANGUAGE,
        APP_THEME,
        NOTIFICATION_REMINDER,
        RATING_FEEDBACK,
        PRIVACY_POLICY
    }

}
