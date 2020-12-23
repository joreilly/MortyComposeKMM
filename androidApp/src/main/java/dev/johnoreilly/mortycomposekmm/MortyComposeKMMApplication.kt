package dev.johnoreilly.mortycomposekmm

import android.app.Application
import dev.johnoreilly.mortycomposekmm.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MortyComposeKMMApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MortyComposeKMMApplication)
            modules(appModule)
        }
    }

}