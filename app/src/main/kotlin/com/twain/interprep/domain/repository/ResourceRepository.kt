package com.twain.interprep.domain.repository

import com.twain.interprep.data.dao.ResourceDAO
import com.twain.interprep.data.dao.ResourceDAO.ResourceWithLinks
import com.twain.interprep.data.model.Resource
import com.twain.interprep.data.model.ResourceLink
import kotlinx.coroutines.flow.Flow

interface ResourceRepository {
    suspend fun getAllResourceWithLinks(): Flow<List<Pair<Resource, List<ResourceLink>>>>
    suspend fun insertResource(resource: Resource): Int
    suspend fun getResourceWithLinksByResourceId(id: Int): Flow<ResourceWithLinks>
    suspend fun updateResource(resource: Resource)
    suspend fun deleteResource(resource: Resource)
    suspend fun getResourceWithLinksBySearchText(searchText: String):  Flow<List<ResourceWithLinks>>
}
