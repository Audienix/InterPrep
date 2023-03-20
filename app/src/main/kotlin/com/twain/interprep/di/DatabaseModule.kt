package com.twain.interprep.di

import android.content.Context
import androidx.room.Room
import com.twain.interprep.data.dao.InterviewDAO
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
}