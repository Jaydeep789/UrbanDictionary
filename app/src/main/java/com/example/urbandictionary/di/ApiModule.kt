package com.example.urbandictionary.di

import com.example.urbandictionary.network.WordServiceApi
import com.example.urbandictionary.utils.Constants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object ApiModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    @Singleton
    @Provides
    fun providesWordApi(retrofit: Retrofit): WordServiceApi {
        return retrofit.create(WordServiceApi::class.java)
    }
}
