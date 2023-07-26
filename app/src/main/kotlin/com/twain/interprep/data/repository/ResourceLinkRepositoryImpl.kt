package com.twain.interprep.data.repository

import androidx.annotation.WorkerThread
import com.twain.interprep.data.dao.ResourceLinkDAO
import com.twain.interprep.data.model.ResourceLink
import com.twain.interprep.domain.repository.ResourceLinkRepository

class ResourceLinkRepositoryImpl(private val linkDAO: ResourceLinkDAO) : ResourceLinkRepository {
    @WorkerThread
    override suspend fun insertResourceLink(link: ResourceLink) = linkDAO.insertLink(link).toInt()

    @WorkerThread
    override suspend fun insertAllResourceLink(links: List<ResourceLink>, resourceId: Int) =
        linkDAO.insertAllLinks(links, resourceId)

    @WorkerThread
    override suspend fun updateResourceLink(link: ResourceLink) { linkDAO.updateLink(link) }

    override suspend fun deleteResourceLink(link: ResourceLink) {
        linkDAO.deleteLink(link)
    }
    override suspend fun deleteResourceLinkByResourceId(id: Int) {
        linkDAO.deleteLinkByResourceId(id)
    }
}

