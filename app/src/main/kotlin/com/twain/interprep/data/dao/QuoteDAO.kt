package com.twain.interprep.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.twain.interprep.data.model.Quote
import com.twain.interprep.util.StringConstants.Companion.DB_TABLE_QUOTES
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuotes(quotes: List<Quote>)

    @Query("SELECT * FROM $DB_TABLE_QUOTES")
    fun getAllQuotes(): Flow<List<Quote>>
}