package com.twain.interprep.presentation.ui.modules.interview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.twain.interprep.data.model.Quote
import com.twain.interprep.domain.usecase.quotes.QuoteUseCase
import com.twain.interprep.presentation.ui.modules.common.BaseViewModel
import com.twain.interprep.helper.CoroutineContextDispatcher
import com.twain.interprep.helper.IntPair
import com.twain.interprep.helper.PrefManager
import com.twain.interprep.helper.PrefManager.Companion.NUM_QUOTE_INSERTED
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(
    contextProvider: CoroutineContextDispatcher,
    private val quoteUseCase: QuoteUseCase,
    private val prefManager: PrefManager,
) : BaseViewModel(contextProvider) {

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
//        val message = ExceptionHandler.parse(exception)
    }

    var quotesList: List<Quote> by mutableStateOf(emptyList())

    fun insertQuotes(quotes: List<Quote>) = launchCoroutineIO {
        viewModelScope.launch {
            if (quotes.size > prefManager.getInt(IntPair.PREV_NUM_QUOTES_INSERTED)){
                quoteUseCase.insertQuotesUseCase.invoke(quotes)
                prefManager.putInt(NUM_QUOTE_INSERTED, quotes.size)
            }
        }
    }

    fun getQuotes() = launchCoroutineIO {
        quoteUseCase.getQuotesUseCase.invoke().collect {
            quotesList = it
        }
    }
}
