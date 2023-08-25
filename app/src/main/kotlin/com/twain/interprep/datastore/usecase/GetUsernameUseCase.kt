package com.twain.interprep.datastore.usecase

import com.twain.interprep.datastore.DataStoreRepository

class GetUsernameUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke() = dataStoreRepository.getUsername()
}
