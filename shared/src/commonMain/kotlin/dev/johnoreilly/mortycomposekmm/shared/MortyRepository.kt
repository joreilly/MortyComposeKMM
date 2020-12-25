package dev.johnoreilly.mortycomposekmm.shared

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloExperimental
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.network.http.ApolloHttpNetworkTransport
import dev.johnoreilly.mortycomposekmm.GetCharacterQuery
import dev.johnoreilly.mortycomposekmm.GetCharactersQuery
import dev.johnoreilly.mortycomposekmm.GetEpisodesQuery
import kotlinx.coroutines.flow.single

@ApolloExperimental
class MortyRepository {
    private val apolloClient = ApolloClient(
        networkTransport = ApolloHttpNetworkTransport(
            serverUrl = "https://rickandmortyapi.com/graphql",
            headers = mapOf(
                "Accept" to "application/json",
                "Content-Type" to "application/json",
            )
        )
    )

    suspend fun getCharacters(page: Int): GetCharactersQuery.Characters? {
        val response = apolloClient.query(GetCharactersQuery(Input.optional(page))).execute().single()
        return response.data?.characters
    }

    suspend fun getCharacter(characterId: String): GetCharacterQuery.Character? {
        val response = apolloClient.query(GetCharacterQuery(characterId)).execute().single()
        return response.data?.character
    }

    suspend fun getEpisodes(page: Int): GetEpisodesQuery.Episodes? {
        val response = apolloClient.query(GetEpisodesQuery(Input.optional(page))).execute().single()
        return response.data?.episodes
    }

}