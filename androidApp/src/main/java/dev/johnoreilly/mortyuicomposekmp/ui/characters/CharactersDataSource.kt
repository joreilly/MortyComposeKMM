package dev.johnoreilly.mortyuicomposekmp.ui.characters

import androidx.paging.PagingSource
import dev.johnoreilly.mortyuicomposekmp.GetCharactersQuery
import dev.johnoreilly.mortyuicomposekmp.shared.MortyRepository

class CharactersDataSource(private val repository: MortyRepository) : PagingSource<Int, GetCharactersQuery.Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetCharactersQuery.Result> {
        val pageNumber = params.key ?: 0

        val charactersResponse = repository.getCharacters(pageNumber)
        val characters = charactersResponse.data?.characters?.resultsFilterNotNull()

        val prevKey = if (pageNumber > 0) pageNumber - 1 else null
        val nextKey = charactersResponse.data?.characters?.info?.next
        return LoadResult.Page(data = characters ?: emptyList(), prevKey = prevKey, nextKey = nextKey)
    }
}