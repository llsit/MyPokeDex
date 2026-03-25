package com.roastkoff.mypokedex.di

import com.roastkoff.mypokedex.navigation.Navigator
import com.roastkoff.mypokedex.navigation.Screen
import org.koin.dsl.module

val commonModule = module {
    single {
        Navigator(startDestination = Screen.Home)
    }
}