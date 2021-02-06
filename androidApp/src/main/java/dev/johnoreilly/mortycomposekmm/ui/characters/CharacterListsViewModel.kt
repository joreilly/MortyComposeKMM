package dev.johnoreilly.mortycomposekmm.ui.characters

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kuuurt.paging.multiplatform.helpers.asCommonFlow
import dev.johnoreilly.mortycomposekmm.fragment.CharacterDetail
import dev.johnoreilly.mortycomposekmm.shared.MortyRepository
import kotlinx.coroutines.flow.Flow

class CharacterListsViewModel(private val repository: MortyRepository): ViewModel() {

//    val characters: Flow<PagingData<CharacterDetail>> = Pager(PagingConfig(pageSize = 20)) {
//        CharactersDataSource(repository)
//    }.flow

    val characters = repository.characterPagingData

    suspend fun getCharacter(characterId: String): CharacterDetail? {
        return repository.getCharacter(characterId)
    }
}