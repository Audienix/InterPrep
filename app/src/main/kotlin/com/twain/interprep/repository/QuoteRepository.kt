package com.twain.interprep.repository

import androidx.annotation.WorkerThread
import com.twain.interprep.data.dao.InterviewDAO
import com.twain.interprep.data.dao.QuoteDAO
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.Quote
import kotlinx.coroutines.flow.Flow

class QuoteRepository(private val quoteDAO: QuoteDAO) {

    val allQuotes: Flow<List<Quote>> = quoteDAO.getAllQuotes()

    @WorkerThread
    suspend fun insert(quotes: List<Quote>) {
        quoteDAO.insertQuotes(quotes)
    }
}