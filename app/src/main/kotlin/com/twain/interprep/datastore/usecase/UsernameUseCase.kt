package com.twain.interprep.datastore.usecase

import com.twain.interprep.datastore.DataStoreRepository

class UsernameUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend fun getUsername() = dataStoreRepository.getUsername()
    suspend fun setUsername(username: String) = dataStoreRepository.setUsername(username)
}
