package com.roastkoff.mypokedex.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roastkoff.mypokedex.domain.GetPokemonUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getPokemonUseCase: GetPokemonUseCase
) : ViewModel() {

    private val _pokemonList = MutableStateFlow<List<Pokemon>>(emptyList())
    val pokemonList = _pokemonList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private var currentPage = 0
    private var isLastPage = false

    init {
        if (_pokemonList.value.isEmpty()) {
            loadNextPage()
        }
    }

    fun loadNextPage() {
        if (_isLoading.value || isLastPage) return

        viewModelScope.launch {
            _isLoading.value = true

            getPokemonUseCase(page = currentPage, pageSize = 20)
                .collect { result ->
                    result.onSuccess { newItems ->
                        if (newItems.isEmpty()) {
                            isLastPage = true
                        } else {
                            _pokemonList.update { currentList ->
                                currentList + newItems
                            }
                            currentPage++
                        }
                    }
                        .onFailure { error ->
                            error.printStackTrace()
                        }

                    _isLoading.value = false
                }
        }
    }

    fun refresh() {
        currentPage = 0
        isLastPage = false
        _pokemonList.value = emptyList()
        loadNextPage()
    }
}
