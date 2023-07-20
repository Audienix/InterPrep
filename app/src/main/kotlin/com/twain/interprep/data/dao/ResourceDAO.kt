package com.twain.interprep.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.OnConflictStrategy
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

    data class ResourceWithLinks(
        @Embedded val resource: Resource,
        @Relation(
            parentColumn = "resourceId",
            entityColumn = "resourceId"
        )
        val links: List<ResourceLink>
    )
}