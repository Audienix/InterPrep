package com.twain.interprep.di

import com.twain.interprep.domain.repository.InterviewRepository
import com.twain.interprep.domain.repository.QuoteRepository
import com.twain.interprep.domain.usecase.interview.InsertInterviewUseCase
import com.twain.interprep.domain.usecase.interview.InterviewUseCase
import com.twain.interprep.domain.usecase.quotes.GetQuotesUseCase
import com.twain.interprep.domain.usecase.quotes.InsertQuotesUseCase
import com.twain.interprep.domain.usecase.quotes.QuoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class UseCaseModule {

    @Singleton
    @Provides
    fun provideQuotesUseCase(quoteRepository: QuoteRepository): QuoteUseCase{
        return QuoteUseCase(
            insertQuotesUseCase = InsertQuotesUseCase(quoteRepository),
            getQuotesUseCase = GetQuotesUseCase(quoteRepository)
        )
    }

    @Singleton
    @Provides
    fun provideInterviewUseCase(interviewRepository: InterviewRepository): InterviewUseCase{
        return InterviewUseCase(
            insertInterviewUseCase = InsertInterviewUseCase(interviewRepository),
        )
    }
}
