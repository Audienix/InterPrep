package com.twain.interprep.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.twain.interprep.helper.Constants
import com.twain.interprep.helper.Constants.Companion.DB_TABLE_RESOURCES

@Entity(tableName = DB_TABLE_RESOURCES)
data class Resource(
    @PrimaryKey(autoGenerate = true) val resourceId: Int,
    val topic: String,
    val subtopic: String?,
    val links: List<ResourceLink>,
)

@Entity(tableName = Constants.DB_TABLE_RESOURCE_LINKS)
data class ResourceLink(
    @PrimaryKey(autoGenerate = true) val linkId: Int,
    val linkDescription: String,
    val link: String
)