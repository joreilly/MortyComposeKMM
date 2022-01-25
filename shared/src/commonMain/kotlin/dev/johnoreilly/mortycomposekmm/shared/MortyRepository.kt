package dev.johnoreilly.mortycomposekmm.shared

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.kuuurt.paging.multiplatform.Pager
import com.kuuurt.paging.multiplatform.PagingConfig
import com.kuuurt.paging.multiplatform.PagingData
import com.kuuurt.paging.multiplatform.PagingResult
import com.kuuurt.paging.multiplatform.helpers.cachedIn
import dev.johnoreilly.mortycomposekmm.*
import dev.johnoreilly.mortycomposekmm.fragment.CharacterDetail
import dev.johnoreilly.mortycomposekmm.fragment.EpisodeDetail
import dev.johnoreilly.mortycomposekmm.fragment.LocationDetail
import dev.johnoreilly.mortycomposekmm.shared.util.CommonFlow
import dev.johnoreilly.mortycomposekmm.shared.util.asCommonFlow
import kotlinx.coroutines.MainScope


class MortyRepository {
    private val scope = MainScope()

    // Creates a 10MB MemoryCacheFactory
    val cacheFactory = MemoryCacheFactory(maxSizeBytes = 10 * 1024 * 1024)

    private val apolloClient = ApolloClient.Builder()
        .serverUrl("https://rickandmortyapi.com/graphql")
        .normalizedCache(cacheFactory)
        .build()

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


    private val pagingConfig = PagingConfig(pageSize = 20, enablePlaceholders = false)

    // also accessed from iOS
    val characterPager = Pager(clientScope = scope, config = pagingConfig, initialKey = 1,
        getItems = { currentKey, size ->
            val charactersResponse = getCharacters(currentKey)
            val items = charactersResponse.results.mapNotNull { it?.characterDetail }
            PagingResult(
                items = items,
                currentKey = currentKey,
                prevKey = { null },
                nextKey = { charactersResponse.info.next }
            )
        }
    )

    val characterPagingData: CommonFlow<PagingData<CharacterDetail>>
        get() = characterPager.pagingData
            .cachedIn(scope) // cachedIn from AndroidX Paging. on iOS, this is a no-op
            .asCommonFlow() // So that iOS can consume the Flow


    val episodePager = Pager(clientScope = scope, config = pagingConfig, initialKey = 1,
        getItems = { currentKey, size ->
            val episodesResponse = getEpisodes(currentKey)
            val items = episodesResponse.results.mapNotNull { it?.episodeDetail }
            PagingResult(
                items = items,
                currentKey = currentKey,
                prevKey = { null },
                nextKey = { episodesResponse.info.next }
            )
        }
    )

    val episodePagingData: CommonFlow<PagingData<EpisodeDetail>>
        get() = episodePager.pagingData
            .cachedIn(scope)
            .asCommonFlow()

}