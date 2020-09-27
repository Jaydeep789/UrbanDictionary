package com.example.urbandictionary.network

import retrofit2.http.GET
import retrofit2.http.Query

interface WordServiceApi {

    @GET("/define")
    suspend fun getWord(
        @Query("term") word: String,
        @Query("rapidapi-key") key: String
    ): NetworkWordContainer
}
