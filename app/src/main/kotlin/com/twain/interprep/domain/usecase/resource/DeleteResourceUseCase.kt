package com.twain.interprep.domain.usecase.resource

import com.twain.interprep.data.model.Resource
import com.twain.interprep.domain.repository.ResourceLinkRepository
import com.twain.interprep.domain.repository.ResourceRepository

class DeleteResourceUseCase(
    private val resourceRepository: ResourceRepository
) {
    suspend operator fun invoke(resource: Resource) {
        resourceRepository.deleteResource(resource)
    }
}
