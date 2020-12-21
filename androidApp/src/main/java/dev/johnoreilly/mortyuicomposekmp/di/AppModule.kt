package dev.johnoreilly.mortyuicomposekmp.di

import dev.johnoreilly.mortyuicomposekmp.shared.MortyRepository
import dev.johnoreilly.mortyuicomposekmp.ui.characters.CharacterListsViewModel
import dev.johnoreilly.mortyuicomposekmp.ui.episodes.EpisodesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val mortyAppModule = module {
    viewModel { CharacterListsViewModel(get()) }
    viewModel { EpisodesListViewModel(get()) }

    single { MortyRepository() }
}


// Gather all app modules
val appModule = listOf(mortyAppModule)