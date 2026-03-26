package com.roastkoff.mypokedex.domain

import com.roastkoff.mypokedex.data.PokemonRepository
import com.roastkoff.mypokedex.model.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface GetPokemonUseCase {
    operator fun invoke(page: Int, pageSize: Int): Flow<Result<List<Pokemon>>>
}

class GetPokemonUseCaseImpl(
    private val repository: PokemonRepository
) : GetPokemonUseCase {
    override fun invoke(
        page: Int,
        pageSize: Int
    ): Flow<Result<List<Pokemon>>> = flow {
        val offset = page * pageSize
        val result = repository.getPokemonList(limit = pageSize, offset = offset)
            .map { list ->
                list.sortedBy { it.id }
            }
        emit(result)
    }
}