package dev.johnoreilly.mortycomposekmm.ui.characters

import androidx.paging.PagingSource
import dev.johnoreilly.mortycomposekmm.GetCharactersQuery
import dev.johnoreilly.mortycomposekmm.shared.MortyRepository

class CharactersDataSource(private val repository: MortyRepository) : PagingSource<Int, GetCharactersQuery.Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetCharactersQuery.Result> {
        val pageNumber = params.key ?: 0

        val charactersResponse = repository.getCharacters(pageNumber)
        val characters = charactersResponse?.resultsFilterNotNull()

        val prevKey = if (pageNumber > 0) pageNumber - 1 else null
        val nextKey = charactersResponse?.info?.next
        return LoadResult.Page(data = characters ?: emptyList(), prevKey = prevKey, nextKey = nextKey)
    }
}