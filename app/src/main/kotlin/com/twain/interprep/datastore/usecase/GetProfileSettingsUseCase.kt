package com.twain.interprep.datastore.usecase

import com.twain.interprep.datastore.DataStoreRepository

class GetProfileSettingsUseCase(
    private val repository: DataStoreRepository
) {
    suspend operator fun invoke() = repository.getProfileSettings()
}
