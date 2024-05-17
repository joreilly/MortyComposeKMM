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
import dev.johnoreilly.mortycomposekmm.fragment.LocationDetail
import dev.johnoreilly.mortycomposekmm.shared.MortyRepository
import dev.johnoreilly.mortycomposekmm.shared.paging.LocationsDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


open class LocationsViewModel(): ViewModel(), KoinComponent {
    private val repository: MortyRepository by inject()

    val locationsFlow: Flow<PagingData<LocationDetail>> = Pager(PagingConfig(pageSize = 20)) {
        LocationsDataSource(repository)
    }.flow.cachedIn(viewModelScope.coroutineScope)


    private val locationsPagingDataPresenter = object : PagingDataPresenter<LocationDetail>() {
        override suspend fun presentPagingDataEvent(event: PagingDataEvent<LocationDetail>) {
            updateLocationsSnapshotList()
        }
    }

    @NativeCoroutinesState
    val locationsSnapshotList = MutableStateFlow<ItemSnapshotList<LocationDetail>>(viewModelScope, locationsPagingDataPresenter.snapshot())

    init {
        viewModelScope.coroutineScope.launch {
            locationsFlow.collectLatest {
                locationsPagingDataPresenter.collectFrom(it)
            }
        }
    }

    private fun updateLocationsSnapshotList() {
        locationsSnapshotList.value = locationsPagingDataPresenter.snapshot()
    }

    fun getElement(index: Int): LocationDetail? {
        return locationsPagingDataPresenter.get(index)
    }

    suspend fun getLocation(episodeId: String): LocationDetail {
        return repository.getLocation(episodeId)
    }
}