package com.twain.interprep.presentation.ui.modules.profile

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import com.twain.interprep.helper.CoroutineContextDispatcher
import com.twain.interprep.helper.PrefManager
import com.twain.interprep.presentation.ui.modules.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject
import com.twain.interprep.constants.StringConstants
import com.twain.interprep.helper.StringPair


@HiltViewModel
class ProfileViewModel @Inject constructor(
    contextProvider: CoroutineContextDispatcher,
    private val prefManager: PrefManager
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

    fun setProfileSettings() {

        profileSettings = ProfileSettings(

            userName = prefManager.getString(StringPair.UserName),

            preferredLanguage = Language.values().first {
                it.code == prefManager.getString(StringPair.Language)
            },

            appTheme = AppTheme.values().first {
                it.dropDownName == prefManager.getString(StringPair.AppTheme)
            },

            notificationReminder = prefManager.getString(StringPair.Notification)
        )

    }

    fun getDisplayedName(): String {
        if (profileSettings.userName.isEmpty()) return profileSettings.userName
        val splitName = profileSettings.userName.split(" ")
        return if (splitName.size == 1) {
            splitName[0][0] + ("".takeIf { splitName[0].length == 1 } ?: splitName[0][1].toString())
        } else {
            splitName[0][0] + splitName[1][0].toString()
        }
    }

    fun getProfileRowData() =
        listOf(
            ProfileRowData(
                imageVector = Icons.Filled.Person,
                title = "Name",
                label = profileSettings.userName,
                clickAction = ClickAction.NAME
            ),
            ProfileRowData(
                imageVector = Icons.Filled.Info,
                title = "Preferred Language",
                label = profileSettings.preferredLanguage.label,
                clickAction = ClickAction.PREFERRED_LANGUAGE
            )
        )

    fun handleAction(action: ClickAction) {
        when (action) {
            ClickAction.NAME -> TODO()
            ClickAction.PREFERRED_LANGUAGE -> TODO()
            ClickAction.APP_THEME -> TODO()
            ClickAction.NOTIFICATION_REMINDER -> TODO()
            ClickAction.RATING_FEEDBACK -> TODO()
        }
    }
}

data class ProfileRowData(
    val imageVector: ImageVector,
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
    RATING_FEEDBACK
}