package com.twain.interprep.presentation.ui.modules.profile

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import com.twain.interprep.R
import com.twain.interprep.helper.CoroutineContextDispatcher
import com.twain.interprep.presentation.ui.modules.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject
import com.twain.interprep.constants.StringConstants
import com.twain.interprep.datastore.usecase.DataStoreUseCase


@HiltViewModel
class ProfileViewModel @Inject constructor(
    contextProvider: CoroutineContextDispatcher,
    private val dataStoreUseCase: DataStoreUseCase
): BaseViewModel(contextProvider) {

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
//        val message = ExceptionHandler.parse(exception)
    }

    var profileSettings by mutableStateOf(
        ProfileSettings(
            userName = "",
            preferredLanguage = Language.ENGLISH_US,
            appTheme = AppTheme.SYSTEM,
            notificationReminder = StringConstants.NO_NOTIFICATION
        )
    )

    fun setProfileSettings() = launchCoroutineIO {
        dataStoreUseCase.getProfileSettingsUseCase().collect {
            profileSettings = it
        }
    }

    fun getProfileRowData() =
        listOf(
            ProfileRowData(
                imageVector = Icons.Filled.Person,
                title = StringConstants.NAME_TITLE,
                label = profileSettings.userName,
                clickAction = ClickAction.NAME
            ),
            ProfileRowData(
                imageRes = R.drawable.ic_preferred_language,
                title = StringConstants.PREFERRED_LANGUAGE_TITLE,
                label = profileSettings.preferredLanguage.label,
                clickAction = ClickAction.PREFERRED_LANGUAGE
            ),
            ProfileRowData(
                imageRes = R.drawable.ic_app_theme,
                title = StringConstants.APP_THEME_TITLE,
                label = profileSettings.appTheme.label,
                clickAction = ClickAction.APP_THEME
            ),
            ProfileRowData(
                imageVector = Icons.Filled.Notifications,
                title = StringConstants.NOTIFICATION_KEY_TITLE,
                label = profileSettings.notificationReminder,
                clickAction = ClickAction.NOTIFICATION_REMINDER
            ),
            ProfileRowData(
                imageVector = Icons.Filled.Star,
                title = StringConstants.RATE_FEEDBACK_TITLE,
                label = StringConstants.RATE_FEEDBACK_LABEL,
                clickAction = ClickAction.RATING_FEEDBACK
            ),
            ProfileRowData(
                imageRes = R.drawable.ic_privacy_policy,
                title = StringConstants.PRIVACY_POLICY_TITLE,
                label = StringConstants.PRIVACY_POLICY_LABEL,
                clickAction = ClickAction.PRIVACY_POLICY
            )
        )

    fun handleAction(action: ClickAction) {
        when (action) {
            ClickAction.NAME -> TODO()
            ClickAction.PREFERRED_LANGUAGE -> TODO()
            ClickAction.APP_THEME -> TODO()
            ClickAction.NOTIFICATION_REMINDER -> TODO()
            ClickAction.RATING_FEEDBACK -> TODO()
            ClickAction.PRIVACY_POLICY -> TODO()
        }
    }
}

data class ProfileRowData(
    val imageVector: ImageVector? = null,
    @DrawableRes val imageRes: Int? = null,
    val title: String,
    val label: String,
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
    ENGLISH_US(StringConstants.ENGLISH_US_CODE, StringConstants.ENGLISH_US_DROPDOWN_NAME,
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
