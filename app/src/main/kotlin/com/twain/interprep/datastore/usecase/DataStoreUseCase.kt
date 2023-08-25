package com.twain.interprep.datastore.usecase

data class DataStoreUseCase(
    val getProfileSettingsUseCase: GetProfileSettingsUseCase,
    val setUsernameUseCase: SetUsernameUseCase,
    val getUsernameUseCase: GetUsernameUseCase
)
