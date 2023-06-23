package com.twain.interprep.domain.usecase.quotes

import com.twain.interprep.domain.repository.QuoteRepository

class GetQuotesUseCase (private val quoteRepository: QuoteRepository) {

    operator fun invoke() = quoteRepository.getQuotes()
}