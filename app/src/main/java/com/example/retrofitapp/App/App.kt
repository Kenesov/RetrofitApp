package com.example.retrofitapp.App

import android.app.Application
import com.example.retrofitapp.di.appModule
import com.example.retrofitapp.di.viewModelModule
import org.koin.core.context.KoinContext
import org.koin.core.context.startKoin

class App: Application() {
    companion object {
        lateinit var instance : App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        startKoin {
            modules(listOf(appModule, viewModelModule))
        }

    }
}