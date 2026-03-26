package com.roastkoff.mypokedex.data

import com.roastkoff.mypokedex.model.PokemonDetail
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<TypeEntryDto>,
    val stats: List<StatEntryDto>,
    val abilities: List<AbilityEntryDto>,
    val sprites: SpriteDto
)

@Serializable
data class TypeEntryDto(
    val type: TypeInfoDto
)

@Serializable
data class TypeInfoDto(val name: String)

@Serializable
data class StatEntryDto(
    val base_stat: Int,
    val stat: StatInfoDto
)

@Serializable
data class StatInfoDto(val name: String)

@Serializable
data class AbilityEntryDto(
    val ability: AbilityInfoDto,
    @SerialName("is_hidden") val isHidden: Boolean,
    val slot: Int
)

@Serializable
data class AbilityInfoDto(
    val name: String,
    val url: String
)

@Serializable
data class SpriteDto(
    val other: OtherSpritesDto
)

@Serializable
data class OtherSpritesDto(
    @SerialName("official-artwork") val officialArtwork: OfficialArtworkDto
)

@Serializable
data class OfficialArtworkDto(
    val front_default: String
)

fun PokemonDetailResponse.toDomain(): PokemonDetail {
    return PokemonDetail(
        id = "#${id.toString().padStart(4, '0')}",
        name = name.replaceFirstChar { it.uppercase() },
        heightInMeters = height / 10.0,
        weightInKg = weight / 10.0,
        imageUrl = sprites.other.officialArtwork.front_default,
        types = types.map { it.type.name.uppercase() },
        abilities = abilities.map { it.ability.name },
        hp = stats.find { it.stat.name == "hp" }?.base_stat ?: 0,
        attack = stats.find { it.stat.name == "attack" }?.base_stat ?: 0,
        defense = stats.find { it.stat.name == "defense" }?.base_stat ?: 0,
        specialAttack = stats.find { it.stat.name == "special-attack" }?.base_stat ?: 0,
        specialDefense = stats.find { it.stat.name == "special-defense" }?.base_stat ?: 0,
        speed = stats.find { it.stat.name == "speed" }?.base_stat ?: 0
    )
}