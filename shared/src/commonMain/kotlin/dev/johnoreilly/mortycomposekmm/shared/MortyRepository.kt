package dev.johnoreilly.mortycomposekmm.shared

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo.cache.normalized.normalizedCache
import dev.johnoreilly.mortycomposekmm.GetCharacterQuery
import dev.johnoreilly.mortycomposekmm.GetCharactersQuery
import dev.johnoreilly.mortycomposekmm.GetEpisodeQuery
import dev.johnoreilly.mortycomposekmm.GetEpisodesQuery
import dev.johnoreilly.mortycomposekmm.GetLocationQuery
import dev.johnoreilly.mortycomposekmm.GetLocationsQuery
import dev.johnoreilly.mortycomposekmm.fragment.CharacterDetail
import dev.johnoreilly.mortycomposekmm.fragment.EpisodeDetail
import dev.johnoreilly.mortycomposekmm.fragment.LocationDetail


class MortyRepository {
    // Creates a 10MB MemoryCacheFactory
    val cacheFactory = MemoryCacheFactory(maxSizeBytes = 10 * 1024 * 1024)

    // TODO use persistent cache as well, also inject apolloClient

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
}
