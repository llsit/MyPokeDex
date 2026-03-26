package com.roastkoff.mypokedex

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.roastkoff.mypokedex.navigation.Navigator
import com.roastkoff.mypokedex.navigation.Screen
import com.roastkoff.mypokedex.ui.HomeScreen
import com.roastkoff.mypokedex.ui.PokeDetailScreen
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navigator: Navigator = koinInject()

        NavDisplay(
            backStack = navigator.backStack,
            onBack = { navigator.goBack() },
            entryProvider = { key ->
                when (key) {
                    is Screen.Home -> NavEntry(key) {
                        HomeScreen {
                            navigator.goTo(Screen.Detail(it))
                        }
                    }

                    is Screen.Detail -> NavEntry(key) {
                        PokeDetailScreen(name = key.name, onBackClick = { navigator.goBack() })
                    }

                    else -> {
                        error("Unknown route: $key")
                    }
                }
            }
        )
    }
}