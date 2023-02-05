package com.example.cryptoapp

import android.app.Application
import com.example.cryptoapp.data.AppDatabase
import com.example.cryptoapp.di.applicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

/**
 * Main application - needed for Koin module injections mainly
 */
class CryptoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@CryptoApp)
            modules(applicationModule, contextualModule)
        }
    }

    private val contextualModule = module {
        single {
            AppDatabase.invoke(this@CryptoApp)
        }
    }
}