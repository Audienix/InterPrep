package com.twain.interprep.datastore.usecase

import com.twain.interprep.datastore.DataStoreRepository

class SetAppThemeUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(appTheme: Int) {
        dataStoreRepository.setAppTheme(appTheme)
    }
}
