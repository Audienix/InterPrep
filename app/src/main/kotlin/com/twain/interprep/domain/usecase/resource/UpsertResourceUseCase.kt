package com.twain.interprep.domain.usecase.resource

import com.twain.interprep.data.model.Resource
import com.twain.interprep.domain.repository.ResourceRepository

class UpsertResourceUseCase(private val resourceRepository: ResourceRepository) {
    suspend operator fun invoke(
        resource: Resource
    ) =
        if (isResourceValid(resource)) {
            if (resource.resourceId == 0) {
                resourceRepository.insertResource(resource)
            } else {
                resourceRepository.updateResource(resource)
                resource.resourceId
            }
        } else {
            null
        }

    private fun isResourceValid(resource: Resource) = true
}

