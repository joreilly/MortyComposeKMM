package dev.johnoreilly.mortycomposekmm.ui.episodes

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import org.koin.androidx.compose.getViewModel

@Composable
fun EpisodesListView(bottmBar: @Composable () -> Unit, ) {
    val episodesListViewModel = getViewModel<EpisodesListViewModel>()
    val lazyEpisodeList = episodesListViewModel.episodes.collectAsLazyPagingItems()

    Scaffold(
        topBar = { TopAppBar(title = { Text( "Episodes") }) },
        bottomBar = bottmBar)
    {
        LazyColumn {
            items(lazyEpisodeList) { episode ->
                episode?.let {
                    EpisodesListRowView(episode)
                }
            }
        }
    }
}

