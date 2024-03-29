package com.twain.interprep.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.twain.interprep.constants.StringConstants.SHARED_PREF_NAME
import com.twain.interprep.helper.CoroutineContextDispatcher
import com.twain.interprep.helper.CoroutineContextDispatcherImp
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
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    fun provideCoroutineDispatcherContext(coroutineContextProvider: CoroutineContextDispatcherImp):
            CoroutineContextDispatcher = coroutineContextProvider
}
