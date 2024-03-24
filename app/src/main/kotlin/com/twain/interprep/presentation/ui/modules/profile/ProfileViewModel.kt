package com.twain.interprep.presentation.ui.modules.profile

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.twain.interprep.R
import com.twain.interprep.data.ui.ProfileSettingsData
import com.twain.interprep.data.ui.ProfileSettingsData.ClickAction
import com.twain.interprep.data.ui.ProfileSettingsData.PreferenceItem
import com.twain.interprep.datastore.usecase.DataStoreUseCase
import com.twain.interprep.helper.CoroutineContextDispatcher
import com.twain.interprep.presentation.ui.modules.common.BaseViewModel
import com.twain.interprep.presentation.ui.modules.interview.InterviewViewModel
import com.twain.interprep.presentation.ui.modules.interview.handleBackPress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    contextProvider: CoroutineContextDispatcher,
    private val dataStoreUseCase: DataStoreUseCase
) : BaseViewModel(contextProvider) {

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
//        val message = ExceptionHandler.parse(exception)
    }

    lateinit var appThemeOptions: List<String>
        private set

    lateinit var notificationOptions: List<String>
        private set

    var action: ClickAction? by mutableStateOf(null)
        private set

    var currentPopupValue by mutableStateOf("")

    var preferenceItem by mutableStateOf(PreferenceItem())

    fun getAppThemeOptions(context: Context) {
        appThemeOptions = context.resources.getStringArray(R.array.theme_option).toList()
    }

    fun getNotificationOptions(context: Context) {
        notificationOptions = context.resources.getStringArray(R.array.notification_reminder_option).toList()
    }
    fun setProfileSettings() = launchCoroutineIO {
        dataStoreUseCase.getProfileSettingsUseCase().collect {
            preferenceItem = it
        }
    }

    fun setAction(action: ClickAction, value: String = "") {
        this.action = action
        value.let { currentPopupValue = it }
    }

    fun setName(name: String) {
        preferenceItem = preferenceItem.copy(
            userName = name
        )
        action = null

        launchCoroutineIO {
            dataStoreUseCase.usernameUseCase.setUsername(name)
        }
    }

    fun setLanguage(language: String, langCode: String) {
        preferenceItem = preferenceItem.copy(
            preferredLanguage = language
        )
        action = null

        launchCoroutineIO {
            dataStoreUseCase.languageUseCase.setLanguage(language, langCode)
        }
    }

    fun getProfileSettingsItemDataList(context: Context) =
        listOf(
            ProfileSettingsData.ProfileSettingsItemData(
                imageVector = Icons.Filled.Person,
                title = R.string.label_setting_name,
                label = preferenceItem.userName,
                clickAction = ClickAction.NAME
            ),
            ProfileSettingsData.ProfileSettingsItemData(
                imageRes = R.drawable.ic_preferred_language,
                title = R.string.label_setting_language,
                label = preferenceItem.preferredLanguage,
                clickAction = ClickAction.PREFERRED_LANGUAGE
            ),
            ProfileSettingsData.ProfileSettingsItemData(
                imageRes = R.drawable.ic_app_theme,
                title = R.string.label_setting_theme,
                label = getAppThemeLabel(preferenceItem.appTheme),
                clickAction = ClickAction.APP_THEME
            ),
            ProfileSettingsData.ProfileSettingsItemData(
                imageVector = Icons.Filled.Notifications,
                title = R.string.label_setting_notification,
                label = getNotificationLabel(preferenceItem.notificationReminder),
                clickAction = ClickAction.NOTIFICATION_REMINDER
            ),
            ProfileSettingsData.ProfileSettingsItemData(
                imageVector = Icons.Filled.Star,
                title = R.string.label_setting_rating,
                label = context.resources.getString(R.string.label_setting_rating_description),
                clickAction = ClickAction.RATING_FEEDBACK
            ),
            ProfileSettingsData.ProfileSettingsItemData(
                imageRes = R.drawable.ic_privacy_policy,
                title = R.string.label_setting_privacy_policy,
                label = context.resources.getString(R.string.label_setting_privacy_policy_description),
                clickAction = ClickAction.PRIVACY_POLICY
            )
        )

    private fun getAppThemeLabel(index: Int): String {
        if (!this::appThemeOptions.isInitialized) return ""
        return appThemeOptions[index]
    }

    private fun getNotificationLabel(index: Int): String {
        if (!this::notificationOptions.isInitialized) return ""
        return notificationOptions[index]
    }

    fun onAppThemeSelected(index: Int) {
        currentPopupValue = appThemeOptions[index]
    }

    fun onNotificationSelected(index: Int) {
        currentPopupValue = notificationOptions[index]
    }

    fun getSelectedAppThemeIndex() = appThemeOptions.indexOf(currentPopupValue)

    fun getSelectedNotificationIndex() = notificationOptions.indexOf(currentPopupValue)

    fun setAppTheme() {
        getSelectedAppThemeIndex().run {
            if (this == -1) return

            launchCoroutineIO {
                dataStoreUseCase.setAppThemeUseCase(this@run)
                action = ClickAction.NONE
                currentPopupValue = ""
            }
        }
    }

    fun setNotification() {
        getSelectedNotificationIndex().run {
            if (this == -1) return

            launchCoroutineIO {
                dataStoreUseCase.setNotificationUseCase(this@run)
                action = ClickAction.NONE
                currentPopupValue = ""
            }
        }
    }
}
