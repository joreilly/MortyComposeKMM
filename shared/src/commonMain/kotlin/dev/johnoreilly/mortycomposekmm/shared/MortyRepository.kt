package dev.johnoreilly.mortycomposekmm.shared

import androidx.paging.ItemSnapshotList
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingDataEvent
import androidx.paging.PagingDataPresenter
import androidx.paging.cachedIn
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.rickclephas.kmp.nativecoroutines.NativeCoroutineScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import dev.johnoreilly.mortycomposekmm.GetCharacterQuery
import dev.johnoreilly.mortycomposekmm.GetCharactersQuery
import dev.johnoreilly.mortycomposekmm.GetEpisodeQuery
import dev.johnoreilly.mortycomposekmm.GetEpisodesQuery
import dev.johnoreilly.mortycomposekmm.GetLocationQuery
import dev.johnoreilly.mortycomposekmm.GetLocationsQuery
import dev.johnoreilly.mortycomposekmm.fragment.CharacterDetail
import dev.johnoreilly.mortycomposekmm.fragment.EpisodeDetail
import dev.johnoreilly.mortycomposekmm.fragment.LocationDetail
import dev.johnoreilly.mortycomposekmm.shared.paging.CharactersDataSource
import dev.johnoreilly.mortycomposekmm.shared.paging.EpisodesDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MortyRepository {
    @NativeCoroutineScope
    val coroutineScope: CoroutineScope = MainScope()

    // Creates a 10MB MemoryCacheFactory
    val cacheFactory = MemoryCacheFactory(maxSizeBytes = 10 * 1024 * 1024)

    // TODO use persistent cache as well, also inject apolloClient

    private val apolloClient = ApolloClient.Builder()
        .serverUrl("https://rickandmortyapi.com/graphql")
        .normalizedCache(cacheFactory)
        .build()

    private val charactersFlow: Flow<PagingData<CharacterDetail>> = Pager(PagingConfig(pageSize = 20)) {
        CharactersDataSource(this)
    }.flow

    private val episodesFlow: Flow<PagingData<EpisodeDetail>> = Pager(PagingConfig(pageSize = 20)) {
        EpisodesDataSource(this)
    }.flow


    // TODO split this logic in to separate class
    val charactersPagingDataPresenter = object : PagingDataPresenter<CharacterDetail>() {
        override suspend fun presentPagingDataEvent(event: PagingDataEvent<CharacterDetail>) {
            updateCharactersSnapshotList()
        }
    }

    val episodesPagingDataPresenter = object : PagingDataPresenter<EpisodeDetail>() {
        override suspend fun presentPagingDataEvent(event: PagingDataEvent<EpisodeDetail>) {
            updateEpisodesSnapshotList()
        }
    }

    @NativeCoroutines
    val charactersSnapshotList = MutableStateFlow<ItemSnapshotList<CharacterDetail>>(charactersPagingDataPresenter.snapshot())

    @NativeCoroutines
    val episodesSnapshotList = MutableStateFlow<ItemSnapshotList<EpisodeDetail>>(episodesPagingDataPresenter.snapshot())


    init {
        collectPagingData()
    }

    private fun updateCharactersSnapshotList() {
        charactersSnapshotList.value = charactersPagingDataPresenter.snapshot()
    }

    private fun updateEpisodesSnapshotList() {
        episodesSnapshotList.value = episodesPagingDataPresenter.snapshot()
    }

    private fun collectPagingData() {
        coroutineScope.launch {
            charactersFlow.collectLatest {
                charactersPagingDataPresenter.collectFrom(it)
            }
        }
        coroutineScope.launch {
            episodesFlow.collectLatest {
                episodesPagingDataPresenter.collectFrom(it)
            }
        }
    }

    suspend fun getCharacters(page: Int): GetCharactersQuery.Characters {
        val response = apolloClient.query(GetCharactersQuery(page)).execute()
        return response.dataAssertNoErrors.characters
    }

    suspend fun getCharacter(characterId: String): CharacterDetail {
        val response = apolloClient.query(GetCharacterQuery(characterId)).execute()
        return response.dataAssertNoErrors.character.characterDetail
    }

    suspend fun getEpisodes(page: Int): GetEpisodesQuery.Episodes {
        val response = apolloClient.query(GetEpisodesQuery(page)).execute()
        return response.dataAssertNoErrors.episodes
    }

    suspend fun getEpisode(episodeId: String): EpisodeDetail {
        val response = apolloClient.query(GetEpisodeQuery(episodeId)).execute()
        return response.dataAssertNoErrors.episode.episodeDetail
    }

    suspend fun getLocations(page: Int): GetLocationsQuery.Locations {
        val response = apolloClient.query(GetLocationsQuery(page)).execute()
        return response.dataAssertNoErrors.locations
    }

    suspend fun getLocation(locationId: String): LocationDetail {
        val response = apolloClient.query(GetLocationQuery(locationId)).execute()
        return response.dataAssertNoErrors.location.locationDetail
    }
}
