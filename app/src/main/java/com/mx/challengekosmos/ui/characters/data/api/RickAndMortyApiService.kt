package com.mx.challengekosmos.ui.characters.data.api

import com.mx.challengekosmos.ui.characters.data.models.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApiService {
    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int): CharacterResponse
}