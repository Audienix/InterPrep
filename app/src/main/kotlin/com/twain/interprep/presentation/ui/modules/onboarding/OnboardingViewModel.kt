package com.twain.interprep.presentation.ui.modules.onboarding

import com.twain.interprep.datastore.usecase.DataStoreUseCase
import com.twain.interprep.helper.CoroutineContextDispatcher
import com.twain.interprep.presentation.ui.modules.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    contextProvider: CoroutineContextDispatcher,
    private val dataStoreUseCase: DataStoreUseCase
) : BaseViewModel(contextProvider) {

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
//        val message = ExceptionHandler.parse(exception)
    }

    fun setUsername(name: String) {
        launchCoroutineIO {
            dataStoreUseCase.usernameUseCase.setUsername(name)
        }
    }

    fun setLanguage(language: String, langCode:String) {
        launchCoroutineIO {
            dataStoreUseCase.languageUseCase.setLanguage(language, langCode)
        }
    }
}
