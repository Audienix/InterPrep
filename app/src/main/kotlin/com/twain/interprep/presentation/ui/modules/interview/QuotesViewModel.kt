package com.twain.interprep.presentation.ui.modules.interview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twain.interprep.data.model.Quote
import com.twain.interprep.domain.usecase.quotes.QuoteUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class QuotesViewModel@Inject constructor(
    private val quoteUseCase: QuoteUseCase,
) : ViewModel() {

    fun insertQuotes(quotes: List<Quote>) = viewModelScope.launch {
        quoteUseCase.insertQuotesUseCase.invoke(quotes)
    }

    fun getQuotes() = viewModelScope.launch {
        quoteUseCase.getQuotesUseCase.invoke()
    }
}