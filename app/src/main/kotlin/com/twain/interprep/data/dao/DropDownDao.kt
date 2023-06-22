package com.twain.interprep.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.twain.interprep.constants.StringConstants.DB_TABLE_DROP_DOWN_OPTION
import com.twain.interprep.data.model.DropDownOption
import com.twain.interprep.data.model.DropDownOptionType
import kotlinx.coroutines.flow.Flow

@Dao
interface DropDownDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDropDownOption(dropDownOption: DropDownOption)

    @Query("SELECT content FROM $DB_TABLE_DROP_DOWN_OPTION WHERE type = :type ORDER BY content DESC")
    fun getAllDropDownOptions(type: DropDownOptionType): Flow<List<String>>
}
