package com.mx.challengekosmos.ui.characters.usecase

import com.mx.challengekosmos.ui.characters.data.api.CharacterRepository

class CharactersUseCaseImpl() : ICharactersUseCase {
    private val repository: CharacterRepository = CharacterRepository()

    override suspend fun getCharacters(page: Int): ICharactersUseCase.Result {
        val result = repository.getCharacters(page)
        return if (result.isEmpty()) {
            ICharactersUseCase.Result.Error("Empty result")
        } else {
            ICharactersUseCase.Result.Success(result)
        }
    }

}