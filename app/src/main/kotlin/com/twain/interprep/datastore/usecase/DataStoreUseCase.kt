package com.twain.interprep.datastore.usecase

data class DataStoreUseCase(
    val onboardingUseCase: OnboardingUseCase,
    val getProfileSettingsUseCase: GetProfileSettingsUseCase,
    val usernameUseCase: UsernameUseCase,
    val languageUseCase: PreferredLanguageUseCase,
    val setAppThemeUseCase: SetAppThemeUseCase,
    val getAppThemeUseCase: GetAppThemeUseCase,
    val setNotificationUseCase: SetNotificationUseCase,
    val getNotificationUseCase: GetNotificationUseCase
)
