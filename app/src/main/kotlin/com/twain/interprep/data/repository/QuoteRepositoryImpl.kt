package com.twain.interprep.data.repository

import androidx.annotation.WorkerThread
import com.twain.interprep.data.dao.QuoteDAO
import com.twain.interprep.data.model.Quote
import com.twain.interprep.domain.repository.QuoteRepository
import kotlinx.coroutines.flow.Flow

class QuoteRepositoryImpl(private val quoteDAO: QuoteDAO) : QuoteRepository {
    @WorkerThread
    override suspend fun insertQuotes(quoteList: List<Quote>) {
        quoteDAO.insertQuotes(quoteList)
    }

    @WorkerThread
    override fun getQuotes(): Flow<List<Quote>> = quoteDAO.getAllQuotes()

    @WorkerThread
    override suspend fun getQuoteById(id: Int): Quote? = quoteDAO.getQuote(quoteId = id)

    @WorkerThread
    override suspend fun deleteQuotes() {
        TODO("Not yet implemented")
    }
}