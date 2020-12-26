package dev.johnoreilly.mortycomposekmm.di

import dev.johnoreilly.mortycomposekmm.shared.MortyRepository
import dev.johnoreilly.mortycomposekmm.ui.characters.CharacterListsViewModel
import dev.johnoreilly.mortycomposekmm.ui.episodes.EpisodesListViewModel
import dev.johnoreilly.mortycomposekmm.ui.locations.LocationsListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val mortyAppModule = module {
    viewModel { CharacterListsViewModel(get()) }
    viewModel { EpisodesListViewModel(get()) }
    viewModel { LocationsListViewModel(get()) }

    single { MortyRepository() }
}


// Gather all app modules
val appModule = listOf(mortyAppModule)