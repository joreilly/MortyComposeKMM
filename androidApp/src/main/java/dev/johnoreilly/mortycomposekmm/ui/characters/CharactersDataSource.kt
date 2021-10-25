package dev.johnoreilly.mortycomposekmm.ui.characters

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.johnoreilly.mortycomposekmm.fragment.CharacterDetail
import dev.johnoreilly.mortycomposekmm.shared.MortyRepository

class CharactersDataSource(private val repository: MortyRepository) : PagingSource<Int, CharacterDetail>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterDetail> {
        val pageNumber = params.key ?: 0

        val charactersResponse = repository.getCharacters(pageNumber)
        val characters = charactersResponse.results.mapNotNull { it?.characterDetail }

        val prevKey = if (pageNumber > 0) pageNumber - 1 else null
        val nextKey = charactersResponse.info.next
        return LoadResult.Page(data = characters, prevKey = prevKey, nextKey = nextKey)
    }

    override fun getRefreshKey(state: PagingState<Int, CharacterDetail>): Int? {
        return null
    }
}