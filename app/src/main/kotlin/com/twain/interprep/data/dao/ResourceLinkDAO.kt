package com.twain.interprep.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.twain.interprep.data.model.Note
import com.twain.interprep.data.model.ResourceLink

@Dao
interface ResourceLinkDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLink(link: ResourceLink): Long

    @Update
    suspend fun updateLink(link: ResourceLink)

    @Transaction
    suspend fun insertAllLinks(links: List<ResourceLink>, resourceId: Int) {
        links.forEach { link ->
            if (link.linkId == 0) {
                insertLink(link.copy(resourceId = resourceId))
            } else {
                updateLink(link)
            }
        }
    }

    @Delete
    suspend fun deleteLink(link: ResourceLink)

    @Query("DELETE FROM resource_link where resourceId = :id")
    suspend fun deleteLinkByResourceId(id: Int)
}
