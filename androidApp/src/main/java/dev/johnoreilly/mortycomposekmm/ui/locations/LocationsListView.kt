package dev.johnoreilly.mortycomposekmm.ui.locations

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import dev.johnoreilly.mortycomposekmm.fragment.EpisodeDetail
import dev.johnoreilly.mortycomposekmm.fragment.LocationDetail
import dev.johnoreilly.mortycomposekmm.ui.MainViewModel


@Composable
fun LocationsListView(viewModel: MainViewModel, bottomBar: @Composable () -> Unit, locationSelected: (location: LocationDetail) -> Unit) {
    val lazyLocationsList = viewModel.locations.collectAsLazyPagingItems()

    Scaffold(
        topBar = { TopAppBar(title = { Text( "Locations") }) },
        bottomBar = bottomBar)
    {
        LazyColumn(contentPadding = it) {
            items(lazyLocationsList) { location ->
                location?.let {
                    LocationsListRowView(location, locationSelected)
                }
            }
        }
    }
}
