package com.roastkoff.mypokedex

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.roastkoff.mypokedex.di.commonModule
import com.roastkoff.mypokedex.di.dataModule
import com.roastkoff.mypokedex.di.domainModule
import com.roastkoff.mypokedex.di.viewModelModule
import org.koin.core.context.startKoin

fun main() = application {
    startKoin {
        printLogger()
        modules(commonModule, dataModule, domainModule, viewModelModule)
    }
    Window(
        onCloseRequest = ::exitApplication,
        title = "MyPokeDeX",
    ) {
        App()
    }
}