package com.mx.challengekosmos.ui.characters.data.api

import com.mx.challengekosmos.ui.characters.data.models.RMCharacter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CharacterRepository {
    private val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()

    private val service = retrofit.create(RickAndMortyApiService::class.java)

    suspend fun getCharacters(page: Int): List<RMCharacter> {
        return service.getCharacters(page).results
    }

    companion object {
        const val BASE_URL = "https://rickandmortyapi.com/api/"
    }
}