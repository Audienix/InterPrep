package com.twain.interprep.datastore.usecase

import com.twain.interprep.datastore.DataStoreRepository

class SetUsernameUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(
        username: String
    ) {
        dataStoreRepository.setUsername(username)
    }
}
