package dev.johnoreilly.mortycomposekmm.ui

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.johnoreilly.mortycomposekmm.fragment.CharacterDetail
import dev.johnoreilly.mortycomposekmm.fragment.EpisodeDetail
import dev.johnoreilly.mortycomposekmm.fragment.LocationDetail
import dev.johnoreilly.mortycomposekmm.shared.MortyRepository
import dev.johnoreilly.mortycomposekmm.ui.episodes.EpisodesDataSource
import dev.johnoreilly.mortycomposekmm.ui.locations.LocationsDataSource
import kotlinx.coroutines.flow.Flow


class MainViewModel(private val repository: MortyRepository): ViewModel() {

    // currently only using MultiplatformPaging library for character data
    val characters = repository.characterPagingData

    // continuing to use androidx paging library directly (as constrast) for
    // episode and location lists
    val episodes: Flow<PagingData<EpisodeDetail>> = Pager(PagingConfig(pageSize = 20)) {
        EpisodesDataSource(repository)
    }.flow

    val locations: Flow<PagingData<LocationDetail>> = Pager(PagingConfig(pageSize = 20)) {
        LocationsDataSource(repository)
    }.flow


    suspend fun getCharacter(characterId: String): CharacterDetail? {
        return repository.getCharacter(characterId)
    }

    suspend fun getEpisode(episodeId: String): EpisodeDetail? {
        return repository.getEpisode(episodeId)
    }

    suspend fun getLocation(episodeId: String): LocationDetail? {
        return repository.getLocation(episodeId)
    }
}