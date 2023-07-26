package com.twain.interprep.domain.usecase.resourceLink

import com.twain.interprep.data.model.ResourceLink
import com.twain.interprep.domain.repository.ResourceLinkRepository
/**
 * Insert the given ResourceLink if it is not in the database
 *
 * @return the linkId of the ResourceLink inserted or linkId of the ResourceLink if it is already in
 * the database
 */
class InsertResourceLinkUseCase(private val resourceLinkRepository: ResourceLinkRepository) {
    suspend operator fun invoke(
        link: ResourceLink
    ): Int? = if (isLinkValid(link)) {
        if (link.linkId == 0) {
            resourceLinkRepository.insertResourceLink(link)
        } else {
            link.linkId
        }
    } else {
        null
    }

    private fun isLinkValid(link: ResourceLink) = true
}

