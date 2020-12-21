package dev.johnoreilly.mortyuicomposekmp

import android.app.Application
import dev.johnoreilly.mortyuicomposekmp.di.appModule
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