package com.twain.interprep.domain.usecase.quotes

import com.twain.interprep.data.model.Quote
import com.twain.interprep.domain.repository.QuoteRepository

class InsertQuotesUseCase (private val quoteRepository: QuoteRepository) {

    suspend operator fun invoke(quotes: List<Quote>) {
        quoteRepository.insertQuotes(quotes)
    }
}