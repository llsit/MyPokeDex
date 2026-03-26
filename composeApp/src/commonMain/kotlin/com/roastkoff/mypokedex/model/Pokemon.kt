package com.roastkoff.mypokedex.model

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.Serializable

@Serializable
data class Pokemon(
    val id: String,
    val name: String,
    val types: List<String>,
    val imageUrl: String,
) {
    val backgroundColor: Color
        get() = when (types.firstOrNull()?.lowercase()) {
            "grass" -> Color(0xFFE2F9E1)
            "fire" -> Color(0xFFFDE1E1)
            "water" -> Color(0xFFE1F1FD)
            "electric" -> Color(0xFFFEF6E1)
            "fairy" -> Color(0xFFFDE1F6)
            "ghost" -> Color(0xFFEDE1FD)
            else -> Color(0xFFEEEEEE)
        }

    val routeId: String
        get() = name.lowercase()
}