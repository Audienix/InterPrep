package com.twain.interprep.di

import com.twain.interprep.data.db.DBManager
import com.twain.interprep.data.repository.InterviewRepositoryImpl
import com.twain.interprep.data.repository.NoteRepositoryImpl
import com.twain.interprep.data.repository.QuoteRepositoryImpl
import com.twain.interprep.data.repository.ResourceLinkRepositoryImpl
import com.twain.interprep.data.repository.ResourceRepositoryImpl
import com.twain.interprep.domain.repository.InterviewRepository
import com.twain.interprep.domain.repository.NoteRepository
import com.twain.interprep.domain.repository.QuoteRepository
import com.twain.interprep.domain.repository.ResourceLinkRepository
import com.twain.interprep.domain.repository.ResourceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providesInterviewRepository(database: DBManager): InterviewRepository {
        return InterviewRepositoryImpl(database.interviewDao())
    }

    @Provides
    @Singleton
    fun providesNoteRepository(database: DBManager): NoteRepository {
        return NoteRepositoryImpl(database.noteDao())
    }

    @Provides
    @Singleton
    fun providesQuoteRepository(database: DBManager): QuoteRepository {
        return QuoteRepositoryImpl(database.quoteDao())
    }

    @Provides
    @Singleton
    fun providesResourceRepository(database: DBManager): ResourceRepository {
        return ResourceRepositoryImpl(database.resourceDao())
    }

    @Provides
    @Singleton
    fun providesResourceLinkRepository(database: DBManager): ResourceLinkRepository {
        return ResourceLinkRepositoryImpl(database.resourceLinkDao())
    }
}
