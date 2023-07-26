package com.twain.interprep.data.repository

import androidx.annotation.WorkerThread
import com.twain.interprep.data.dao.ResourceDAO
import com.twain.interprep.data.model.Resource
import com.twain.interprep.data.model.ResourceLink
import com.twain.interprep.domain.repository.ResourceRepository
import kotlinx.coroutines.flow.map

class ResourceRepositoryImpl(private val resourceDAO: ResourceDAO) : ResourceRepository {
    @WorkerThread
    override suspend fun getAllResourceWithLinks() = resourceDAO.getAllResourceLinkMap().map {
        val result = mutableListOf<Pair<Resource, List<ResourceLink>>>()
        it.forEach { resourceAndLinks ->
            result.add(resourceAndLinks.resource to resourceAndLinks.links)
        }
        result
    }

    @WorkerThread
    override suspend fun insertResource(resource: Resource) =
        resourceDAO.insertResource(resource).toInt()

    @WorkerThread
    override suspend fun getResourceWithLinksByResourceId(id: Int) =
        resourceDAO.getResourceWithLinksByResourceId(id)

    @WorkerThread
    override suspend fun updateResource(resource: Resource) {
      resourceDAO.updateResource(resource)
    }

    override suspend fun deleteResource(resource: Resource) {
        resourceDAO.deleteResource(resource)
    }
}

