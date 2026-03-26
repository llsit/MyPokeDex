package com.roastkoff.mypokedex.di

import com.roastkoff.mypokedex.data.KtorPokemonApi
import com.roastkoff.mypokedex.data.PokemonApi
import com.roastkoff.mypokedex.data.PokemonRepository
import com.roastkoff.mypokedex.data.PokemonRepositoryImpl
import com.roastkoff.mypokedex.domain.GetPokemonDetailUseCase
import com.roastkoff.mypokedex.domain.GetPokemonDetailUseCaseImpl
import com.roastkoff.mypokedex.domain.GetPokemonUseCase
import com.roastkoff.mypokedex.domain.GetPokemonUseCaseImpl
import com.roastkoff.mypokedex.navigation.Navigator
import com.roastkoff.mypokedex.navigation.Screen
import com.roastkoff.mypokedex.network.createHttpClient
import com.roastkoff.mypokedex.ui.HomeViewModel
import com.roastkoff.mypokedex.ui.PokeDetailViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val commonModule = module {
    single {
        Navigator(startDestination = Screen.Home)
    }
}

val dataModule = module {
    single { createHttpClient() }
    singleOf(::KtorPokemonApi) bind PokemonApi::class
    singleOf(::PokemonRepositoryImpl) bind PokemonRepository::class
}

val domainModule = module {
    factoryOf(::GetPokemonUseCaseImpl) bind GetPokemonUseCase::class
    factoryOf(::GetPokemonDetailUseCaseImpl) bind GetPokemonDetailUseCase::class
}

val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModel { params ->
        PokeDetailViewModel(
            name = params.get(),
            getPokemonDetailUseCase = get()
        )
    }
}