package com.twain.interprep.datastore.usecase

data class DataStoreUseCase(
    val onboardingUseCase: OnboardingUseCase,
    val getProfileSettingsUseCase: GetProfileSettingsUseCase,
    val usernameUseCase: UsernameUseCase,
    val languageUseCase: PreferredLanguageUseCase
)
