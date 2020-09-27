package com.example.urbandictionary.di

import android.content.Context
import com.example.urbandictionary.database.WordDatabase
import com.example.urbandictionary.network.WordServiceApi
import com.example.urbandictionary.repository.WordRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AndroidModule {

    @Provides
    @Singleton
    fun providesContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Singleton
    @Provides
    fun provideRepository(
        api: WordServiceApi,
        context: Context,
        wordDatabase: WordDatabase
    ): WordRepository {
        return WordRepository(api, context, wordDatabase)
    }
}
