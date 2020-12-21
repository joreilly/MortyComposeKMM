package dev.johnoreilly.mortyuicomposekmp.shared

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloExperimental
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.network.http.ApolloHttpNetworkTransport
import dev.johnoreilly.mortyuicomposekmp.GetCharactersQuery
import dev.johnoreilly.mortyuicomposekmp.GetEpisodesQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
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

    suspend fun getCharacters(page: Int): Response<GetCharactersQuery.Data> {
        val response = apolloClient.query(GetCharactersQuery(Input.optional(page))).execute().single()
        return response
    }

    suspend fun getEpisodes(page: Int): Response<GetEpisodesQuery.Data> {
        val response = apolloClient.query(GetEpisodesQuery(Input.optional(page))).execute().single()
        return response
    }

}