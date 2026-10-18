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
import dev.johnoreilly.mortycomposekmm.fragment.LocationDetail
import dev.johnoreilly.mortycomposekmm.shared.MortyRepository
import dev.johnoreilly.mortycomposekmm.shared.paging.LocationsDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


open class LocationsViewModel(): ViewModel(), KoinComponent {
    private val repository: MortyRepository by inject()

    val locationsFlow: Flow<PagingData<LocationDetail>> = Pager(PagingConfig(pageSize = 20)) {
        LocationsDataSource(repository)
    }.flow.cachedIn(viewModelScope)


    private val locationsPagingDataPresenter = object : PagingDataPresenter<LocationDetail>() {
        override suspend fun presentPagingDataEvent(event: PagingDataEvent<LocationDetail>) {
            updateLocationsSnapshotList()
        }
    }

    // A plain List (not paging's ItemSnapshotList) so Swift Export maps it to a Swift array
    private val _locationsSnapshotList = MutableStateFlow<List<LocationDetail?>>(locationsPagingDataPresenter.snapshot())
    val locationsSnapshotList: StateFlow<List<LocationDetail?>> = _locationsSnapshotList.asStateFlow()

    // Set when the first page fails to load (the list would otherwise just stay empty), so the
    // iOS list can show an error with a retry; cleared once a load succeeds.
    private val _locationsLoadError = MutableStateFlow<String?>(null)
    val locationsLoadError: StateFlow<String?> = _locationsLoadError.asStateFlow()

    init {
        viewModelScope.launch {
            locationsPagingDataPresenter.loadStateFlow.collect { loadStates ->
                _locationsLoadError.value =
                    if (loadStates?.refresh is LoadState.Error) "Could not load locations" else null
            }
        }
        viewModelScope.launch {
            locationsFlow.collectLatest {
                locationsPagingDataPresenter.collectFrom(it)
            }
        }
    }

    private fun updateLocationsSnapshotList() {
        _locationsSnapshotList.value = locationsPagingDataPresenter.snapshot()
    }

    fun getElement(index: Int): LocationDetail? {
        return locationsPagingDataPresenter.get(index)
    }

    fun retry() {
        locationsPagingDataPresenter.retry()
    }

    suspend fun getLocation(episodeId: String): LocationDetail {
        return repository.getLocation(episodeId)
    }
}