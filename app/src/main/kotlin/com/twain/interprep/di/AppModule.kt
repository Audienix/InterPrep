package com.twain.interprep.di

import com.twain.interprep.datastore.usecase.DataStoreUseCase
import com.twain.interprep.helper.LocalizationViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideLocalizationManager(
        dataStoreUseCase: DataStoreUseCase
    ): LocalizationViewModel {
        return LocalizationViewModel(dataStoreUseCase)
    }
}
