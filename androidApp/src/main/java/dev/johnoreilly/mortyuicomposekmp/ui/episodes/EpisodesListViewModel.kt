package dev.johnoreilly.mortyuicomposekmp.ui.episodes

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.johnoreilly.mortyuicomposekmp.GetEpisodesQuery
import dev.johnoreilly.mortyuicomposekmp.shared.MortyRepository
import kotlinx.coroutines.flow.Flow

class EpisodesListViewModel(private val repository: MortyRepository): ViewModel() {

    val episodes: Flow<PagingData<GetEpisodesQuery.Result>> = Pager(PagingConfig(pageSize = 20)) {
        EpisodesDataSource(repository)
    }.flow


}