package com.twain.interprep.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.twain.interprep.constants.StringConstants

@Entity(tableName = StringConstants.DB_TABLE_DROP_DOWN_OPTION, primaryKeys = ["type", "content"])
data class DropDownOption(
    val type: DropDownOptionType,
    val content: String
)

enum class DropDownOptionType {
    INTERVIEW_TYPE,
    ROLE,
    TOPIC,
    SUBTOPIC
}
