package com.example.remote.retrofit.services

import com.example.remote.retrofit.data.MarvelResponse
import com.example.remote.retrofit.data.RemoteCharacterModel
import com.marvel.model.Character
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersService {
    @GET("characters")
    suspend fun fetchAllCharacters(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
        @Query("orderBy") orderBy: String = "name"
    ): Response<MarvelResponse<List<RemoteCharacterModel>>>

    @GET("characters/{id}")
    suspend fun fetCharacterById(
        @Path("id") id: Int
    ): Response<MarvelResponse<List<RemoteCharacterModel>>>
}