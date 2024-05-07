package com.twain.interprep.presentation.ui.modules.profile

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twain.interprep.R
import com.twain.interprep.data.ui.ProfileSettingsData
import com.twain.interprep.data.ui.ProfileSettingsData.ClickAction
import com.twain.interprep.data.ui.ProfileSettingsData.PreferenceItem
import com.twain.interprep.datastore.usecase.DataStoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dataStoreUseCase: DataStoreUseCase
) : ViewModel() {

    lateinit var appThemeOptions: List<String>
        private set

    var action: ClickAction? by mutableStateOf(null)
        private set

    var currentPopupValue by mutableStateOf("")

    var preferenceItem by mutableStateOf(PreferenceItem())

    fun getAppThemeOptions(context: Context) {
        appThemeOptions = context.resources.getStringArray(R.array.theme_option).toList()
    }

    fun setProfileSettings() = viewModelScope.launch(Dispatchers.IO) {
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

        viewModelScope.launch(Dispatchers.IO) {
            dataStoreUseCase.usernameUseCase.setUsername(name)
        }
    }

    fun setLanguage(language: String, langCode: String) {
        preferenceItem = preferenceItem.copy(
            preferredLanguage = language
        )
        action = null

        viewModelScope.launch(Dispatchers.IO) {
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
            //TODO Enable this after beta release
//            ProfileSettingsData.ProfileSettingsItemData(
//                imageVector = Icons.Filled.Notifications,
//                title = R.string.label_setting_notification,
//                label = preferenceItem.notificationReminder,
//                clickAction = ClickAction.NOTIFICATION_REMINDER
//            ),
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

    fun getAppThemeLabel(index: Int): String {
        if (!this::appThemeOptions.isInitialized) return ""
        return appThemeOptions[index]
    }


    fun onAppThemeSelected(index: Int) {
        currentPopupValue = appThemeOptions[index]
    }

    fun getSelectedAppThemeIndex() = appThemeOptions.indexOf(currentPopupValue)

    fun setAppTheme() {
        getSelectedAppThemeIndex().run {
            if (this == -1) return

            viewModelScope.launch(Dispatchers.IO) {
                dataStoreUseCase.setAppThemeUseCase(this@run)
                action = ClickAction.NONE
                currentPopupValue = ""
            }
        }
    }
}
