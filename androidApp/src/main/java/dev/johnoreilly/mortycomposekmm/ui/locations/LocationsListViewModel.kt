package dev.johnoreilly.mortycomposekmm.ui.locations

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.johnoreilly.mortycomposekmm.fragment.LocationDetail
import dev.johnoreilly.mortycomposekmm.shared.MortyRepository
import kotlinx.coroutines.flow.Flow

class LocationsListViewModel(private val repository: MortyRepository): ViewModel() {

    val locations: Flow<PagingData<LocationDetail>> = Pager(PagingConfig(pageSize = 20)) {
        LocationsDataSource(repository)
    }.flow


    suspend fun getLocation(episodeId: String): LocationDetail? {
        return repository.getLocation(episodeId)
    }

}