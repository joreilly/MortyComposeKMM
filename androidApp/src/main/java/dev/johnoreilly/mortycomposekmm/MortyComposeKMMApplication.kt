package dev.johnoreilly.mortycomposekmm

import android.app.Application
import com.surrus.common.di.initKoin
import dev.johnoreilly.mortycomposekmm.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class MortyComposeKMMApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidLogger()
            androidContext(this@MortyComposeKMMApplication)
            modules(appModule)
        }
    }

}