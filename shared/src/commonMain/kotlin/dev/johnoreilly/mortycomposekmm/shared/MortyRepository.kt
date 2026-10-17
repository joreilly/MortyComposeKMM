package dev.johnoreilly.mortycomposekmm.shared

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloResponse
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo.cache.normalized.normalizedCache
import com.apollographql.apollo.exception.ApolloHttpException
import dev.johnoreilly.mortycomposekmm.GetCharacterQuery
import dev.johnoreilly.mortycomposekmm.GetCharactersQuery
import dev.johnoreilly.mortycomposekmm.GetEpisodeQuery
import dev.johnoreilly.mortycomposekmm.GetEpisodesQuery
import dev.johnoreilly.mortycomposekmm.GetLocationQuery
import dev.johnoreilly.mortycomposekmm.GetLocationsQuery
import dev.johnoreilly.mortycomposekmm.fragment.CharacterDetail
import dev.johnoreilly.mortycomposekmm.fragment.EpisodeDetail
import dev.johnoreilly.mortycomposekmm.fragment.LocationDetail
import kotlinx.coroutines.delay


class MortyRepository {
    // Creates a 10MB MemoryCacheFactory
    val cacheFactory = MemoryCacheFactory(maxSizeBytes = 10 * 1024 * 1024)

    // TODO use persistent cache as well, also inject apolloClient

    private val apolloClient = ApolloClient.Builder()
        .serverUrl("https://rickandmortyapi.com/graphql")
        .normalizedCache(cacheFactory)
        .build()

    // rickandmortyapi.com rate-limits per client (HTTP 429), and the character avatars come from
    // the same host, so a burst of image loads can get a query refused. Retry those, honouring
    // Retry-After, rather than failing the page: a failed first page leaves the list empty.
    private suspend fun <D : Operation.Data> ApolloCall<D>.executeRetryingRateLimit(): ApolloResponse<D> {
        var attempt = 0
        while (true) {
            val response = execute()
            val rateLimited = response.exception?.let { findRateLimit(it) } ?: return response
            if (attempt == MAX_RATE_LIMIT_RETRIES) return response
            val retryAfterSeconds = rateLimited.headers
                .firstOrNull { it.name.equals("Retry-After", ignoreCase = true) }
                ?.value?.trim()?.toLongOrNull()
            delay(
                retryAfterSeconds?.let { (it * 1000).coerceIn(500, MAX_RETRY_DELAY_MS) }
                    ?: (1000L shl attempt)
            )
            attempt++
        }
    }

    // With the default CacheFirst policy the network error is attached as a suppressed exception
    // of the cache miss, so look through causes and suppressed exceptions for the 429.
    private fun findRateLimit(throwable: Throwable): ApolloHttpException? {
        if (throwable is ApolloHttpException && throwable.statusCode == 429) return throwable
        throwable.suppressedExceptions.forEach { suppressed -> findRateLimit(suppressed)?.let { return it } }
        return throwable.cause?.let { findRateLimit(it) }
    }

    private companion object {
        const val MAX_RATE_LIMIT_RETRIES = 3
        const val MAX_RETRY_DELAY_MS = 10_000L
    }

    suspend fun getCharacters(page: Int): GetCharactersQuery.Characters {
        val response = apolloClient.query(GetCharactersQuery(page)).executeRetryingRateLimit()
        return response.dataAssertNoErrors.characters
    }

    suspend fun getCharacter(characterId: String): CharacterDetail {
        val response = apolloClient.query(GetCharacterQuery(characterId)).executeRetryingRateLimit()
        return response.dataAssertNoErrors.character.characterDetail
    }

    suspend fun getEpisodes(page: Int): GetEpisodesQuery.Episodes {
        val response = apolloClient.query(GetEpisodesQuery(page)).executeRetryingRateLimit()
        return response.dataAssertNoErrors.episodes
    }

    suspend fun getEpisode(episodeId: String): EpisodeDetail {
        val response = apolloClient.query(GetEpisodeQuery(episodeId)).executeRetryingRateLimit()
        return response.dataAssertNoErrors.episode.episodeDetail
    }

    suspend fun getLocations(page: Int): GetLocationsQuery.Locations {
        val response = apolloClient.query(GetLocationsQuery(page)).executeRetryingRateLimit()
        return response.dataAssertNoErrors.locations
    }

    suspend fun getLocation(locationId: String): LocationDetail {
        val response = apolloClient.query(GetLocationQuery(locationId)).executeRetryingRateLimit()
        return response.dataAssertNoErrors.location.locationDetail
    }
}
