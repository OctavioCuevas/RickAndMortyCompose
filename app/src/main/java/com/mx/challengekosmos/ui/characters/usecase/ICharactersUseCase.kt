package com.mx.challengekosmos.ui.characters.usecase

import com.mx.challengekosmos.ui.characters.data.models.RMCharacter

interface ICharactersUseCase {
    suspend fun getCharacters(page: Int): Result

    sealed class Result {
        class Success(val characters: List<RMCharacter>) : Result()
        class Error(val errorMessage: String?) : Result()
    }
}