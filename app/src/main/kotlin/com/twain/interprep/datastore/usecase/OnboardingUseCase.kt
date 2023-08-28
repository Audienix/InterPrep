package com.twain.interprep.datastore.usecase

import com.twain.interprep.datastore.DataStoreRepository

class OnboardingUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend fun getOnboardingStatus() = dataStoreRepository.getOnboardingStatus()
    suspend fun setOnboardingStatus(status: Boolean) =
        dataStoreRepository.setOnboardingStatus(status)
}
