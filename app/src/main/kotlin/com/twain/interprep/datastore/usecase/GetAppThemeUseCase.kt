package com.twain.interprep.datastore.usecase

import com.twain.interprep.datastore.DataStoreRepository

class GetAppThemeUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke() = dataStoreRepository.getAppTheme()
}
