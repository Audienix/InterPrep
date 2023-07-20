package com.twain.interprep.domain.usecase.resourceLink

data class ResourceLinkUseCase(
    val insertAllResourceLinkUseCase: InsertAllResourceLinkUseCase,
    val insertResourceLinkUseCase: InsertResourceLinkUseCase,
    val updateResourceLinkUseCase: UpdateResourceLinkUseCase,
    val deleteResourceLinkUseCase: DeleteResourceLinkUseCase
)

