package com.roastkoff.mypokedex.di

fun initKoinIos() {
    initKoin {
        modules(commonModule, dataModule, domainModule, viewModelModule)
    }
}