package dev.johnoreilly.mortycomposekmm.ui.locations

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import dev.johnoreilly.mortycomposekmm.fragment.LocationDetail
import dev.johnoreilly.mortycomposekmm.shared.viewmodel.LocationsViewModel
import org.koin.compose.koinInject


@Composable
fun LocationsListView(locationSelected: (location: LocationDetail) -> Unit) {
    val viewModel: LocationsViewModel = koinInject()
    val lazyLocationsList = viewModel.locationsFlow.collectAsLazyPagingItems()

    LazyColumn {
        items(
            count = lazyLocationsList.itemCount,
            key = lazyLocationsList.itemKey { it.name },
            contentType = lazyLocationsList.itemContentType { "MyPagingItems" }
        ) { index ->
            val location = lazyLocationsList[index]
            location?.let {
                LocationsListRowView(location, locationSelected)
            }
        }
    }
}
