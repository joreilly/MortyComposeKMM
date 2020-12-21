package dev.johnoreilly.mortyuicomposekmp.ui.episodes

import androidx.paging.PagingSource
import dev.johnoreilly.mortyuicomposekmp.GetCharactersQuery
import dev.johnoreilly.mortyuicomposekmp.GetEpisodesQuery
import dev.johnoreilly.mortyuicomposekmp.shared.MortyRepository

class EpisodesDataSource(private val repository: MortyRepository) : PagingSource<Int, GetEpisodesQuery.Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetEpisodesQuery.Result> {
        val pageNumber = params.key ?: 0

        val episodesResponse = repository.getEpisodes(pageNumber)
        val episodes = episodesResponse.data?.episodes?.resultsFilterNotNull()

        val prevKey = if (pageNumber > 0) pageNumber - 1 else null
        val nextKey = episodesResponse.data?.episodes?.info?.next
        return LoadResult.Page(data = episodes ?: emptyList(), prevKey = prevKey, nextKey = nextKey)
    }
}