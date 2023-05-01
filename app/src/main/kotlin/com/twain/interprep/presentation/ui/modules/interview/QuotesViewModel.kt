package com.twain.interprep.presentation.ui.modules.interview

import androidx.lifecycle.viewModelScope
import com.twain.interprep.data.model.Quote
import com.twain.interprep.domain.usecase.quotes.QuoteUseCase
import com.twain.interprep.presentation.ui.modules.common.BaseViewModel
import com.twain.interprep.util.CoroutineContextProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(
    contextProvider: CoroutineContextProvider,
    private val quoteUseCase: QuoteUseCase,
) : BaseViewModel(contextProvider) {

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
//        val message = ExceptionHandler.parse(exception)
    }

    fun insertQuotes(quotes: List<Quote>) = viewModelScope.launch {
        viewModelScope.launch {
            quoteUseCase.insertQuotesUseCase.invoke(quotes)
        }
    }

    fun getQuotes() = viewModelScope.launch {
        quoteUseCase.getQuotesUseCase.invoke()
    }
}
