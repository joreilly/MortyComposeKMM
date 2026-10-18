package dev.johnoreilly.mortycomposekmm.shared.viewmodel

import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingDataEvent
import androidx.paging.PagingDataPresenter
import androidx.paging.cachedIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.johnoreilly.mortycomposekmm.fragment.CharacterDetail
import dev.johnoreilly.mortycomposekmm.shared.MortyRepository
import dev.johnoreilly.mortycomposekmm.shared.paging.CharactersDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


open class CharactersViewModel(): ViewModel(), KoinComponent {
    private val repository: MortyRepository by inject()

    val charactersFlow: Flow<PagingData<CharacterDetail>> = Pager(PagingConfig(pageSize = 20)) {
        CharactersDataSource(repository)
    }.flow.cachedIn(viewModelScope)


    private val charactersPagingDataPresenter = object : PagingDataPresenter<CharacterDetail>() {
        override suspend fun presentPagingDataEvent(event: PagingDataEvent<CharacterDetail>) {
            updateCharactersSnapshotList()
        }
    }

    // A plain List (not paging's ItemSnapshotList) so Swift Export maps it to a Swift array
    private val _charactersSnapshotList = MutableStateFlow<List<CharacterDetail?>>(charactersPagingDataPresenter.snapshot())
    val charactersSnapshotList: StateFlow<List<CharacterDetail?>> = _charactersSnapshotList.asStateFlow()

    // Set when the first page fails to load (the list would otherwise just stay empty), so the
    // iOS list can show an error with a retry; cleared once a load succeeds.
    private val _charactersLoadError = MutableStateFlow<String?>(null)
    val charactersLoadError: StateFlow<String?> = _charactersLoadError.asStateFlow()

    init {
        viewModelScope.launch {
            charactersPagingDataPresenter.loadStateFlow.collect { loadStates ->
                _charactersLoadError.value =
                    if (loadStates?.refresh is LoadState.Error) "Could not load characters" else null
            }
        }
        viewModelScope.launch {
            charactersFlow.collectLatest {
                charactersPagingDataPresenter.collectFrom(it)
            }
        }
    }

    private fun updateCharactersSnapshotList() {
        _charactersSnapshotList.value = charactersPagingDataPresenter.snapshot()
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