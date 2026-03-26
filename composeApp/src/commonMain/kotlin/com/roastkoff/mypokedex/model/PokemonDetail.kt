package com.roastkoff.mypokedex.model

data class PokemonDetail(
    val id: String,
    val name: String,
    val heightInMeters: Double,
    val weightInKg: Double,
    val imageUrl: String,
    val types: List<String>,
    val abilities: List<String>,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int
) {
    val primaryType: String?
        get() = types.firstOrNull()
}