package com.roastkoff.mypokedex

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "MyPokeDeX",
    ) {
        App()
    }
}