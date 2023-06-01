package com.twain.interprep.domain.repository

import com.twain.interprep.data.model.Quote
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {

    suspend fun insertQuotes(quoteList: List<Quote>)

    fun getQuotes(): Flow<List<Quote>>

    suspend fun getQuoteById(id: Int): Quote?

    suspend fun deleteQuotes()
}