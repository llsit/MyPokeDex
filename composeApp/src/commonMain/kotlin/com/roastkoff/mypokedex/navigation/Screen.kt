package com.roastkoff.mypokedex.navigation

import com.roastkoff.mypokedex.ui.Pokemon
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object Home : Screen()

    @Serializable
    data class Detail(val pokemon: Pokemon) : Screen()
}