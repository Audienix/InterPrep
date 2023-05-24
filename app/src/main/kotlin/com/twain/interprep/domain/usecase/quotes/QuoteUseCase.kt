package com.twain.interprep.domain.usecase.quotes

data class QuoteUseCase(
    val insertQuotesUseCase: InsertQuotesUseCase,
    val getQuotesUseCase: GetQuotesUseCase
)