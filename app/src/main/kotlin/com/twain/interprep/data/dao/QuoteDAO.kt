package com.twain.interprep.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.twain.interprep.data.model.Quote
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuotes(quotes: List<Quote>)

    @Query("SELECT * FROM quote")
    fun getAllQuotes(): Flow<List<Quote>>

    @Query("SELECT * FROM quote WHERE quoteId = :quoteId")
    fun getQuote(quoteId: Int): Quote

    @Query("DELETE FROM quote")
    suspend fun deleteAllQuotes()
}