package com.roastkoff.mypokedex.data

import com.roastkoff.mypokedex.ui.Pokemon

interface PokemonRepository {
    suspend fun getPokemonList(limit: Int, offset: Int): Result<List<Pokemon>>
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
                types = emptyList(),
                backgroundColorHex = 0xFFF3F3F3
            )
        }

        return Result.success(result)
    }
}