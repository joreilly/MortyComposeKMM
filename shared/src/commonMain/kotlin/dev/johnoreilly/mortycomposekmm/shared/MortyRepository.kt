package dev.johnoreilly.mortycomposekmm.shared

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.http.HttpNetworkTransport
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

    private val apolloClient = ApolloClient(
        networkTransport = HttpNetworkTransport(
            serverUrl = "https://rickandmortyapi.com/graphql",
            headers = mapOf(
                "Accept" to "application/json",
                "Content-Type" to "application/json",
            )
        )
    )

    suspend fun getCharacters(page: Int): GetCharactersQuery.Characters? {
        val response = apolloClient.query(GetCharactersQuery(page))
        return response.data?.characters
    }

    suspend fun getCharacter(characterId: String): CharacterDetail? {
        val response = apolloClient.query(GetCharacterQuery(characterId))
        return response.data?.character?.fragments?.characterDetail
    }

    suspend fun getEpisodes(page: Int): GetEpisodesQuery.Episodes? {
        val response = apolloClient.query(GetEpisodesQuery(page))
        return response.data?.episodes
    }

    suspend fun getEpisode(episodeId: String): EpisodeDetail? {
        val response = apolloClient.query(GetEpisodeQuery(episodeId))
        return response.data?.episode?.fragments?.episodeDetail
    }

    suspend fun getLocations(page: Int): GetLocationsQuery.Locations? {
        val response = apolloClient.query(GetLocationsQuery(page))
        return response.data?.locations
    }

    suspend fun getLocation(locationId: String): LocationDetail? {
        val response = apolloClient.query(GetLocationQuery(locationId))
        return response.data?.location?.fragments?.locationDetail
    }


    // also accessed from iOS
    val characterPager = Pager(
        clientScope = scope,
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false // Ignored on iOS
        ),
        initialKey = 1,
        getItems = { currentKey, size ->
            val charactersResponse = getCharacters(currentKey)
            val items = charactersResponse?.results?.mapNotNull { it?.fragments?.characterDetail } ?: emptyList()
            PagingResult(
                items = items,
                currentKey = currentKey,
                prevKey = { null },
                nextKey = { charactersResponse?.info?.next }
            )
        }
    )

    val characterPagingData: CommonFlow<PagingData<CharacterDetail>>
        get() = characterPager.pagingData
            .cachedIn(scope) // cachedIn from AndroidX Paging. on iOS, this is a no-op
            .asCommonFlow() // So that iOS can consume the Flow
}