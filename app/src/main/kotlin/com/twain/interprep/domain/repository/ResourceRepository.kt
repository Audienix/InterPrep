package com.twain.interprep.domain.repository

import com.twain.interprep.data.model.Resource
import com.twain.interprep.data.model.ResourceLink
import kotlinx.coroutines.flow.Flow

interface ResourceRepository {
    suspend fun getAllResourceWithLinks(): Flow<List<Pair<Resource, List<ResourceLink>>>>
}
