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
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.Note
import com.twain.interprep.data.model.Resource
import com.twain.interprep.data.model.ResourceLink
import kotlinx.coroutines.flow.Flow

@Dao
interface ResourceDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResource(resource: Resource)

    @Update
    suspend fun updateResource(resource: Resource)

    @Delete
    suspend fun deleteResource(resource: Resource)

    @Transaction
    @Query("SELECT * FROM resource")
    fun getAllResourceLinkMap(): Flow<List<ResourceWithLinks>>

    data class ResourceWithLinks(
        @Embedded val resource: Resource,
        @Relation(
            parentColumn = "resourceId",
            entityColumn = "resourceId"
        )
        val links: List<ResourceLink>
    )
}