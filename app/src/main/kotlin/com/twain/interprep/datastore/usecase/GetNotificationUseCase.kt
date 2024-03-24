package com.twain.interprep.datastore.usecase

import com.twain.interprep.datastore.DataStoreRepository

class GetNotificationUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke() = dataStoreRepository.getNotification()
}
