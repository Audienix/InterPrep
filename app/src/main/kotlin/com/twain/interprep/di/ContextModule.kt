package com.twain.interprep.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.twain.interprep.helper.CoroutineContextDispatcher
import com.twain.interprep.helper.CoroutineContextDispatcherImp
import com.twain.interprep.helper.PrefManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ActivityRetainedComponent::class)
@Module
object ContextModule {

    @Provides
    fun provideAppContext(@ApplicationContext context: Application): Context {
        return context.applicationContext
    }

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("missing_seven", Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun providePrefManager(preferences: SharedPreferences) = PrefManager(preferences)

    @Provides
    fun provideCoroutineDispatcherContext(coroutineContextProvider: CoroutineContextDispatcherImp):
            CoroutineContextDispatcher = coroutineContextProvider
}
