package com.twain.interprep.domain.repository

import com.twain.interprep.data.model.Note
import com.twain.interprep.data.model.Resource
import com.twain.interprep.data.model.ResourceLink

interface ResourceLinkRepository {
    suspend fun insertResourceLink(link: ResourceLink): Int

    suspend fun insertAllResourceLink(links: List<ResourceLink>, resourceId: Int)

    suspend fun updateResourceLink(link: ResourceLink)

    suspend fun deleteResourceLink(link: ResourceLink)

    suspend fun deleteResourceLinkByResourceId(id: Int)
}
