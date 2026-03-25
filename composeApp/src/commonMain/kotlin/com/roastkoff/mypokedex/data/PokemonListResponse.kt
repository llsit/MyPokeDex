package com.roastkoff.mypokedex.data

import kotlinx.serialization.Serializable

@Serializable
data class PokemonListResponse(
    val results: List<PokemonItemDto>
)

@Serializable
data class PokemonItemDto(
    val name: String,
    val url: String
) {
    val id: String
        get() = url.split("/").dropLast(1).last()

    val imageUrl: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
}