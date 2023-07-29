package com.twain.interprep.di

import android.content.Context
import androidx.room.Room
import com.twain.interprep.data.dao.InterviewDAO
import com.twain.interprep.data.dao.NoteDAO
import com.twain.interprep.data.dao.QuoteDAO
import com.twain.interprep.data.dao.ResourceDAO
import com.twain.interprep.data.dao.ResourceLinkDAO
import com.twain.interprep.data.db.DBManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideInterPrepDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, DBManager::class.java, "interprep_database.db")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideInterviewDao(database: DBManager): InterviewDAO {
        return database.interviewDao()
    }

    @Singleton
    @Provides
    fun provideNoteDao(database: DBManager): NoteDAO {
        return database.noteDao()
    }

    @Singleton
    @Provides
    fun provideResourceDao(database: DBManager): ResourceDAO {
        return database.resourceDao()
    }

    @Singleton
    @Provides
    fun provideQuoteDao(database: DBManager): QuoteDAO {
        return database.quoteDao()
    }
    @Singleton
    @Provides
    fun provideResourceLinkDao(database: DBManager): ResourceLinkDAO {
        return database.resourceLinkDao()
    }
}
