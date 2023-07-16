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
}
