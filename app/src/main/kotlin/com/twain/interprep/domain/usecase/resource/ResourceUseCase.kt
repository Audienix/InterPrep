package com.twain.interprep.domain.usecase.resource

data class ResourceUseCase(
    val getAllResourcesWithLinksUseCase: GetAllResourcesWithLinksUseCase,
    val upsertResourceUseCase: UpsertResourceUseCase,
    val getResourceWithLinksByResourceId: GetResourceWithLinksByResourceId,
    val deleteResourceUseCase: DeleteResourceUseCase,
    val getResourceWithLinksBySearchText: GetResourceWithLinksBySearchTextUseCase,
)

