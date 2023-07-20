package com.twain.interprep.domain.usecase.resource

import com.twain.interprep.domain.repository.ResourceRepository

class GetResourceWithLinksByResourceId(private val resourceRepository: ResourceRepository) {
    suspend operator fun invoke(id: Int) = resourceRepository.getResourceWithLinksByResourceId(id)
}
