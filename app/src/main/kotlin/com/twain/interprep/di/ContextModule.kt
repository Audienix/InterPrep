package com.twain.interprep.di

import android.app.Application
import android.content.Context
import com.twain.interprep.util.CoroutineContextDispatcher
import com.twain.interprep.util.CoroutineContextDispatcherImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@InstallIn(ActivityRetainedComponent::class)
@Module
object ContextModule {

    @Provides
    fun provideAppContext(@ApplicationContext context: Application): Context {
        return context.applicationContext
    }

    @Provides
    fun provideCoroutineDispatcherContext(coroutineContextProvider: CoroutineContextDispatcherImp):
            CoroutineContextDispatcher = coroutineContextProvider
}
