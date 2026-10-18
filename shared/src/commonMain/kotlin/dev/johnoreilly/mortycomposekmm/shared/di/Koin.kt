package dev.johnoreilly.mortycomposekmm.shared.di

import dev.johnoreilly.mortycomposekmm.shared.MortyRepository
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(commonModule())
    }

// called by iOS etc. Returns Unit: Swift Export turns a function returning a type it
// doesn't export (KoinApplication) into a fatalError() stub
fun initKoin() {
    initKoin {}
}

fun commonModule() = module {
    single { MortyRepository() }
}
