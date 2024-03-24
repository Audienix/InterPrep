package com.twain.interprep.datastore.usecase

import com.twain.interprep.datastore.DataStoreRepository

class SetNotificationUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(notification: Int) = dataStoreRepository.setNotification(notification)
}
