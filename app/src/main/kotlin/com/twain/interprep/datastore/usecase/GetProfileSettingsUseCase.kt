package com.twain.interprep.datastore.usecase

import com.twain.interprep.datastore.DataStoreRepository

class GetProfileSettingsUseCase(
    val repository: DataStoreRepository
) {
    operator fun invoke() = repository.getProfileSettings()
}
