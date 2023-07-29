package com.twain.interprep.domain.usecase.resourceLink

import com.twain.interprep.data.model.ResourceLink
import com.twain.interprep.domain.repository.ResourceLinkRepository

class DeleteResourceLinkUseCase(private val resourceLinkRepository: ResourceLinkRepository) {
    suspend operator fun invoke(
        link: ResourceLink
    ) = resourceLinkRepository.deleteResourceLink(link)
}
