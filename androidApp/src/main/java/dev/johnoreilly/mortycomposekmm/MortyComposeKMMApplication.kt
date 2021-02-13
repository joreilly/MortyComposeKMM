package dev.johnoreilly.mortycomposekmm

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MortyComposeKMMApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

}