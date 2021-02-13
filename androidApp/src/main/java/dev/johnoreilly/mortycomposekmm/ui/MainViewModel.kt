package dev.johnoreilly.mortycomposekmm.ui

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.johnoreilly.mortycomposekmm.fragment.CharacterDetail
import dev.johnoreilly.mortycomposekmm.fragment.EpisodeDetail
import dev.johnoreilly.mortycomposekmm.fragment.LocationDetail
import dev.johnoreilly.mortycomposekmm.shared.MortyRepository
import dev.johnoreilly.mortycomposekmm.ui.episodes.EpisodesDataSource
import dev.johnoreilly.mortycomposekmm.ui.locations.LocationsDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MortyRepository): ViewModel() {
    val characters = repository.characterPagingData

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