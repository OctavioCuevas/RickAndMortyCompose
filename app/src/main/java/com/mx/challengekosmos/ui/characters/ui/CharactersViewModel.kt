package com.mx.challengekosmos.ui.characters.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.challengekosmos.ui.characters.data.models.RMCharacter
import com.mx.challengekosmos.ui.characters.usecase.CharactersUseCaseImpl
import com.mx.challengekosmos.ui.characters.usecase.ICharactersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharactersViewModel : ViewModel() {
    val useCase = CharactersUseCaseImpl()
    // private val repository = CharacterRepository()

    private val _characters = MutableStateFlow<List<RMCharacter>>(emptyList())
    val characters: StateFlow<List<RMCharacter>> = _characters

    val screenState: StateFlow<State>
        get() = _screenState

    private val _screenState: MutableStateFlow<State> = MutableStateFlow(
        State.Loading,
    )

    fun loadCharacters(page: Int) {
        viewModelScope.launch {
            _screenState.value = State.Loading
            when (val result = useCase.getCharacters(page)) {
                is ICharactersUseCase.Result.Success -> {
                    _screenState.value = State.Success
                    _characters.value = result.characters
                }

                is ICharactersUseCase.Result.Error -> {
                    _screenState.value = State.Error(result.errorMessage)
                }
            }
            // val characters = repository.getCharacters(page)
        }
    }

    sealed class State {

        /**
         * Success Status.
         */
        data object Success : State()

        /**
         * Error.
         */
        class Error(val message: String?) : State()

        /**
         * Waiting for a result
         */
        data object Loading : State()
    }
}