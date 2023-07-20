package com.twain.interprep.domain.usecase.resourceLink

import com.twain.interprep.data.model.ResourceLink
import com.twain.interprep.domain.repository.ResourceLinkRepository

class InsertAllResourceLinkUseCase(private val resourceLinkRepository: ResourceLinkRepository) {
    suspend operator fun invoke(
        links: List<ResourceLink>,
        resourceId: Int
    ) = resourceLinkRepository.insertAllResourceLink(links, resourceId)
}
