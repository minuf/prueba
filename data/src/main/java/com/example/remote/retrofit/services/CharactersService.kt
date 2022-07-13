package com.example.remote.retrofit.services

import com.example.remote.retrofit.data.MarvelResponse
import com.example.remote.retrofit.data.VALUES
import com.marvel.entities.Character
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface CharactersService {
    @GET("characters")
    suspend fun fetchAllCharacters(
        @Query("hash") hash: String = VALUES.hash,
        @Query("ts") ts: String = VALUES.timeStamp,
        @Query("apikey") apikey: String = VALUES.apiKey,
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): Response<MarvelResponse<List<Character>>>

    @GET("characters/{id}")
    suspend fun fetCharacterById(
        @Path("id") id: Int,
        @QueryMap queryMap: Map<String, String> = HashMap(),
        @Query("hash") hash: String = VALUES.hash,
        @Query("ts") ts: String = VALUES.timeStamp,
        @Query("apikey") apikey: String = VALUES.apiKey
    ): Response<MarvelResponse<List<Character>>>
}