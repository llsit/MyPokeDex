package com.roastkoff.mypokedex.data

import com.roastkoff.mypokedex.model.Pokemon

interface PokemonRepository {
    suspend fun getPokemonList(limit: Int, offset: Int): Result<List<Pokemon>>
    suspend fun getPokemonDetail(name: String): Result<PokemonDetailResponse>
}

class PokemonRepositoryImpl(
    private val api: PokemonApi
) : PokemonRepository {
    override suspend fun getPokemonList(
        limit: Int,
        offset: Int
    ): Result<List<Pokemon>> {
        val response = api.fetchList(limit, offset)

        val result = response.results.map { dto ->
            Pokemon(
                id = "#${dto.id.padStart(4, '0')}",
                name = dto.name.replaceFirstChar { it.uppercase() },
                imageUrl = dto.imageUrl,
                types = emptyList()
            )
        }

        return Result.success(result)
    }

    override suspend fun getPokemonDetail(name: String): Result<PokemonDetailResponse> {
        val response = api.fetchDetail(name)

        return Result.success(response)
    }
}