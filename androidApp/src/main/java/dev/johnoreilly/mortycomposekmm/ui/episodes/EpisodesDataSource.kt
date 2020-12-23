package dev.johnoreilly.mortycomposekmm.ui.episodes

import androidx.paging.PagingSource
import dev.johnoreilly.mortycomposekmm.GetEpisodesQuery
import dev.johnoreilly.mortycomposekmm.shared.MortyRepository

class EpisodesDataSource(private val repository: MortyRepository) : PagingSource<Int, GetEpisodesQuery.Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetEpisodesQuery.Result> {
        val pageNumber = params.key ?: 0

        val episodesResponse = repository.getEpisodes(pageNumber)
        val episodes = episodesResponse?.resultsFilterNotNull()

        val prevKey = if (pageNumber > 0) pageNumber - 1 else null
        val nextKey = episodesResponse?.info?.next
        return LoadResult.Page(data = episodes ?: emptyList(), prevKey = prevKey, nextKey = nextKey)
    }
}