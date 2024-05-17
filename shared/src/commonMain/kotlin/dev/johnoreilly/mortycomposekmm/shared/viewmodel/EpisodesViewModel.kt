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
import dev.johnoreilly.mortycomposekmm.fragment.EpisodeDetail
import dev.johnoreilly.mortycomposekmm.shared.MortyRepository
import dev.johnoreilly.mortycomposekmm.shared.paging.EpisodesDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


open class EpisodesViewModel(): ViewModel(), KoinComponent {
    private val repository: MortyRepository by inject()

    val episodesFlow: Flow<PagingData<EpisodeDetail>> = Pager(PagingConfig(pageSize = 20)) {
        EpisodesDataSource(repository)
    }.flow.cachedIn(viewModelScope.coroutineScope)


    private val episodesPagingDataPresenter = object : PagingDataPresenter<EpisodeDetail>() {
        override suspend fun presentPagingDataEvent(event: PagingDataEvent<EpisodeDetail>) {
            updateEpisodesSnapshotList()
        }
    }

    @NativeCoroutinesState
    val episodesSnapshotList = MutableStateFlow<ItemSnapshotList<EpisodeDetail>>(viewModelScope, episodesPagingDataPresenter.snapshot())

    init {
        viewModelScope.coroutineScope.launch {
            episodesFlow.collectLatest {
                episodesPagingDataPresenter.collectFrom(it)
            }
        }
    }

    private fun updateEpisodesSnapshotList() {
        episodesSnapshotList.value = episodesPagingDataPresenter.snapshot()
    }

    fun getElement(index: Int): EpisodeDetail? {
        return episodesPagingDataPresenter.get(index)
    }

    suspend fun getEpisode(episodeId: String): EpisodeDetail {
        return repository.getEpisode(episodeId)
    }
}