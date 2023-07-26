package com.twain.interprep.domain.usecase.resource

import com.twain.interprep.data.model.Resource
import com.twain.interprep.domain.repository.ResourceRepository

/**
 * Insert the given Resource if it is not in the database, if it is in the database, update
 * the instance in the database
 *
 * @return the linkId of the Resource inserted or linkId of the ResourceLink if it is already in
 * the database
 */
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

