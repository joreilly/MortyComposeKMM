package dev.johnoreilly.mortycomposekmm.shared.viewmodel

import androidx.paging.ItemSnapshotList
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingDataEvent
import androidx.paging.PagingDataPresenter
import androidx.paging.cachedIn
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.MutableStateFlow
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.coroutineScope
import dev.johnoreilly.mortycomposekmm.fragment.CharacterDetail
import dev.johnoreilly.mortycomposekmm.shared.MortyRepository
import dev.johnoreilly.mortycomposekmm.shared.paging.CharactersDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


open class CharactersViewModel(): ViewModel(), KoinComponent {
    private val repository: MortyRepository by inject()

    val charactersFlow: Flow<PagingData<CharacterDetail>> = Pager(PagingConfig(pageSize = 20)) {
        CharactersDataSource(repository)
    }.flow.cachedIn(viewModelScope.coroutineScope)


    private val charactersPagingDataPresenter = object : PagingDataPresenter<CharacterDetail>() {
        override suspend fun presentPagingDataEvent(event: PagingDataEvent<CharacterDetail>) {
            updateCharactersSnapshotList()
        }
    }

    @NativeCoroutinesState
    val charactersSnapshotList = MutableStateFlow<ItemSnapshotList<CharacterDetail>>(viewModelScope, charactersPagingDataPresenter.snapshot())

    init {
        viewModelScope.coroutineScope.launch {
            charactersFlow.collectLatest {
                charactersPagingDataPresenter.collectFrom(it)
            }
        }
    }

    private fun updateCharactersSnapshotList() {
        charactersSnapshotList.value = charactersPagingDataPresenter.snapshot()
    }

    fun getElement(index: Int): CharacterDetail? {
        return charactersPagingDataPresenter.get(index)
    }

    suspend fun getCharacter(characterId: String): CharacterDetail {
        return repository.getCharacter(characterId)
    }

}