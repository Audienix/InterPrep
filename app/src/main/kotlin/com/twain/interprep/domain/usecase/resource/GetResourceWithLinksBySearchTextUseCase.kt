package com.twain.interprep.domain.usecase.resource

import com.twain.interprep.domain.repository.ResourceRepository

class GetResourceWithLinksBySearchTextUseCase(private val resourceRepository: ResourceRepository) {
    suspend operator fun invoke(searchText: String) =
        resourceRepository.getResourceWithLinksBySearchText(searchText)
}
