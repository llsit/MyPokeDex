package com.roastkoff.mypokedex.domain

import com.roastkoff.mypokedex.data.PokemonRepository
import com.roastkoff.mypokedex.data.toDomain
import com.roastkoff.mypokedex.model.PokemonDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

interface GetPokemonDetailUseCase {
    operator fun invoke(name: String): Flow<Result<PokemonDetail>>
}

class GetPokemonDetailUseCaseImpl(
    private val repository: PokemonRepository
) : GetPokemonDetailUseCase {

    override fun invoke(name: String): Flow<Result<PokemonDetail>> = flow {
        val result = repository.getPokemonDetail(name)
        emit(result.map { it.toDomain() })
    }.catch { e ->
        emit(Result.failure(e))
    }
}