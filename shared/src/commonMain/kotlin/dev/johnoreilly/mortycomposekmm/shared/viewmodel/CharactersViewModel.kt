package dev.johnoreilly.mortycomposekmm.shared.viewmodel

import androidx.paging.ItemSnapshotList
import androidx.paging.LoadState
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
import kotlinx.coroutines.flow.MutableStateFlow
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
    val charactersSnapshotList: MutableStateFlow<ItemSnapshotList<CharacterDetail>> = MutableStateFlow<ItemSnapshotList<CharacterDetail>>(viewModelScope, charactersPagingDataPresenter.snapshot())

    // Set when the first page fails to load (the list would otherwise just stay empty), so the
    // iOS list can show an error with a retry; cleared once a load succeeds.
    @NativeCoroutinesState
    val charactersLoadError: MutableStateFlow<String?> = MutableStateFlow<String?>(viewModelScope, null)

    init {
        viewModelScope.coroutineScope.launch {
            charactersPagingDataPresenter.loadStateFlow.collect { loadStates ->
                charactersLoadError.value =
                    if (loadStates?.refresh is LoadState.Error) "Could not load characters" else null
            }
        }
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

    fun retry() {
        charactersPagingDataPresenter.retry()
    }

    suspend fun getCharacter(characterId: String): CharacterDetail {
        return repository.getCharacter(characterId)
    }

}