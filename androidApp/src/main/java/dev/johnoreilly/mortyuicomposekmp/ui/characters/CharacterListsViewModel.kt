package dev.johnoreilly.mortyuicomposekmp.ui.characters

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.johnoreilly.mortyuicomposekmp.GetCharactersQuery
import dev.johnoreilly.mortyuicomposekmp.shared.MortyRepository
import kotlinx.coroutines.flow.Flow

class CharacterListsViewModel(private val repository: MortyRepository): ViewModel() {

    val characters: Flow<PagingData<GetCharactersQuery.Result>> = Pager(PagingConfig(pageSize = 20)) {
        CharactersDataSource(repository)
    }.flow


}