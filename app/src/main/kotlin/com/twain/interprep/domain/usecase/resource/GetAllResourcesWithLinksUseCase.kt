package com.twain.interprep.domain.usecase.resource

import com.twain.interprep.domain.repository.ResourceRepository

class GetAllResourcesWithLinksUseCase(private val resourceRepository: ResourceRepository) {
    suspend operator fun invoke() = resourceRepository.getAllResourceWithLinks()
}
