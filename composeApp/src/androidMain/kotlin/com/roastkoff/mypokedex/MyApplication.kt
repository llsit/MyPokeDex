package com.roastkoff.mypokedex

import android.app.Application
import com.roastkoff.mypokedex.di.commonModule
import com.roastkoff.mypokedex.di.dataModule
import com.roastkoff.mypokedex.di.domainModule
import com.roastkoff.mypokedex.di.initKoin
import com.roastkoff.mypokedex.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MyApplication)
            modules(commonModule, dataModule, domainModule, viewModelModule)
            androidLogger()
        }
    }
}