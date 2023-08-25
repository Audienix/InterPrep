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

    var action: ClickAction? by mutableStateOf(null)
        private set

    var currentPopupValue by mutableStateOf("")

    var preferenceItem by mutableStateOf(
        PreferenceItem(
            userName = "",
            preferredLanguage = "",
            appTheme = "",
            notificationReminder = ""
        )
    )

    fun setProfileSettings() = launchCoroutineIO {
        dataStoreUseCase.getProfileSettingsUseCase().collect {
            preferenceItem = it
        }
    }

    fun setAction(action: ClickAction?, value: String?) {
        this.action = action
        value?.let { currentPopupValue = it }
    }

    fun setName(name: String) {
        preferenceItem = preferenceItem.copy(
            userName = name
        )
        action = null

        launchCoroutineIO {
            dataStoreUseCase.setUsernameUseCase(name)
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
                label = preferenceItem.appTheme,
                clickAction = ClickAction.APP_THEME
            ),
            ProfileSettingsData.ProfileSettingsItemData(
                imageVector = Icons.Filled.Notifications,
                title = R.string.label_setting_notification,
                label = preferenceItem.notificationReminder,
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
}
