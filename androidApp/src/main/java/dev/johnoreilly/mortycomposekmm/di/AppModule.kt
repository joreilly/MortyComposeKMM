package dev.johnoreilly.mortycomposekmm.di

import dev.johnoreilly.mortycomposekmm.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel(get()) }
}
