package com.twain.interprep.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.twain.interprep.constants.StringConstants
import com.twain.interprep.constants.StringConstants.DB_TABLE_RESOURCE
import com.twain.interprep.constants.StringConstants.DB_TABLE_RESOURCE_LINKS

@Entity(tableName = DB_TABLE_RESOURCE)
data class Resource(
    @PrimaryKey(autoGenerate = true) val resourceId: Int = 0,
    val topic: String = "",
    val subtopic: String = ""
)
@Entity(
    tableName = DB_TABLE_RESOURCE_LINKS,
    foreignKeys = [ForeignKey(
        entity = Resource::class,
        parentColumns = ["resourceId"],
        childColumns = ["resourceId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class ResourceLink(
    @PrimaryKey(autoGenerate = true) val linkId: Int = 0,
    val resourceId: Int = 0,
    val linkDescription: String = "",
    val link: String = ""
)
