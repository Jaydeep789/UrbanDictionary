package com.example.urbandictionary.di

import android.content.Context
import androidx.room.Room
import com.example.urbandictionary.database.DatabaseWordDao
import com.example.urbandictionary.database.WordDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun providesWordDatabase(@ApplicationContext context: Context): WordDatabase {
        return Room.databaseBuilder(
            context,
            WordDatabase::class.java,
            "database-words"
        ).build()
    }

    @Singleton
    @Provides
    fun providesWordDao(database: WordDatabase): DatabaseWordDao {
        return database.wordDao()
    }
}
