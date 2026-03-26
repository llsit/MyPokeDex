package com.roastkoff.mypokedex.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roastkoff.mypokedex.domain.GetPokemonDetailUseCase
import com.roastkoff.mypokedex.model.PokemonDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PokeDetailViewModel(
    val name: String,
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase,
) : ViewModel() {

    private val _pokemon = MutableStateFlow<PokemonDetail?>(null)
    val pokemon = _pokemon.asStateFlow()

    init {
        fetchDetail(name)
    }

    private fun fetchDetail(name: String) {
        viewModelScope.launch {
            _pokemon.value = null
            getPokemonDetailUseCase(name)
                .collect { result ->
                    result.onSuccess {
                        // Handle success
                        _pokemon.value = it
                    }.onFailure {
                        // Handle error
                        it.printStackTrace()
                    }
                }
        }
    }
}