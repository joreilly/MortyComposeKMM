package dev.johnoreilly.mortycomposekmm.di

import dev.johnoreilly.mortycomposekmm.shared.viewmodel.CharactersViewModel
import dev.johnoreilly.mortycomposekmm.shared.viewmodel.EpisodesViewModel
import dev.johnoreilly.mortycomposekmm.shared.viewmodel.LocationsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { CharactersViewModel() }
    viewModel { EpisodesViewModel() }
    viewModel { LocationsViewModel() }
}
