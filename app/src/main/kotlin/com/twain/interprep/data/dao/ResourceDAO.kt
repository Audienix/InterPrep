package com.twain.interprep.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import androidx.room.Update
import com.twain.interprep.data.model.Resource
import com.twain.interprep.data.model.ResourceLink
import kotlinx.coroutines.flow.Flow

@Dao
interface ResourceDAO {
    @Insert
    suspend fun insertResource(resource: Resource): Long

    @Update
    suspend fun updateResource(resource: Resource)

    @Delete
    suspend fun deleteResource(resource: Resource)

    @Transaction
    @Query("SELECT * FROM resource")
    fun getAllResourceLinkMap(): Flow<List<ResourceWithLinks>>

    @Transaction
    @Query("SELECT * FROM resource where resourceId = :id")
    fun getResourceWithLinksByResourceId(id: Int): Flow<ResourceWithLinks>
    @Transaction
    @Query(
        """SELECT * FROM resource
        INNER JOIN resource_link ON resource.resourceId = resource_link.resourceId
        WHERE resource.subtopic LIKE :searchText 
        or resource.topic LIKE :searchText
        or resource_link.link LIKE :searchText
        or resource_link.linkDescription LIKE :searchText
        GROUP BY resource.resourceId
        """
    )
    fun getResourceWithLinksBySearchText(searchText: String): Flow<List<ResourceWithLinks>>

    data class ResourceWithLinks(
        @Embedded val resource: Resource,
        @Relation(
            parentColumn = "resourceId",
            entityColumn = "resourceId"
        )
        val links: List<ResourceLink>
    )
}
