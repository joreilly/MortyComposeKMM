package dev.johnoreilly.mortycomposekmm.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.johnoreilly.mortycomposekmm.GetCharacterQuery
import dev.johnoreilly.mortycomposekmm.GetCharactersQuery
import dev.johnoreilly.mortycomposekmm.shared.MortyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CharacterListsViewModel(private val repository: MortyRepository): ViewModel() {

    val characters: Flow<PagingData<GetCharactersQuery.Result>> = Pager(PagingConfig(pageSize = 20)) {
        CharactersDataSource(repository)
    }.flow

    suspend fun getCharacter(characterId: String): GetCharacterQuery.Character? {
        return repository.getCharacter(characterId)
    }

}