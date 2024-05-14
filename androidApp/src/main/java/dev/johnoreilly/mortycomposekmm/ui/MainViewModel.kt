package dev.johnoreilly.mortycomposekmm.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dev.johnoreilly.mortycomposekmm.fragment.CharacterDetail
import dev.johnoreilly.mortycomposekmm.fragment.EpisodeDetail
import dev.johnoreilly.mortycomposekmm.fragment.LocationDetail
import dev.johnoreilly.mortycomposekmm.shared.MortyRepository
import dev.johnoreilly.mortycomposekmm.shared.paging.CharactersDataSource
import dev.johnoreilly.mortycomposekmm.shared.paging.EpisodesDataSource
import dev.johnoreilly.mortycomposekmm.shared.paging.LocationsDataSource
import kotlinx.coroutines.flow.Flow


class MainViewModel(private val repository: MortyRepository): ViewModel() {

    val characters: Flow<PagingData<CharacterDetail>> = Pager(PagingConfig(pageSize = 20)) {
        CharactersDataSource(repository)
    }.flow.cachedIn(viewModelScope)


    val episodes: Flow<PagingData<EpisodeDetail>> = Pager(PagingConfig(pageSize = 20)) {
        EpisodesDataSource(repository)
    }.flow.cachedIn(viewModelScope)

    val locations: Flow<PagingData<LocationDetail>> = Pager(PagingConfig(pageSize = 20)) {
        LocationsDataSource(repository)
    }.flow.cachedIn(viewModelScope)


    suspend fun getCharacter(characterId: String): CharacterDetail {
        return repository.getCharacter(characterId)
    }

    suspend fun getEpisode(episodeId: String): EpisodeDetail {
        return repository.getEpisode(episodeId)
    }

    suspend fun getLocation(episodeId: String): LocationDetail {
        return repository.getLocation(episodeId)
    }
}