package com.twain.interprep.presentation.ui.modules.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.twain.interprep.constants.StringConstants
import com.twain.interprep.data.ui.ProfileSettingsData
import com.twain.interprep.data.ui.ProfileSettingsData.ClickAction
import com.twain.interprep.data.ui.ProfileSettingsData.ProfileSettings
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
): BaseViewModel(contextProvider) {

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
//        val message = ExceptionHandler.parse(exception)
    }

    var profileSettings by mutableStateOf(
        ProfileSettings(
            userName = "",
            preferredLanguage = ProfileSettingsData.Language.ENGLISH_US,
            appTheme = ProfileSettingsData.AppTheme.SYSTEM,
            notificationReminder = StringConstants.NO_NOTIFICATION
        )
    )

    fun setProfileSettings() = launchCoroutineIO {
        dataStoreUseCase.getProfileSettingsUseCase().collect {
            profileSettings = it
        }
    }

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
