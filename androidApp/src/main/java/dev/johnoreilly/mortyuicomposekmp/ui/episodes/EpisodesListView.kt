package dev.johnoreilly.mortyuicomposekmp.ui.episodes

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import org.koin.androidx.compose.getViewModel

@Composable
fun EpisodesListView() {
    val episodesListViewModel = getViewModel<EpisodesListViewModel>()
    val lazyEpisodeList = episodesListViewModel.episodes.collectAsLazyPagingItems()

    LazyColumn {
        items(lazyEpisodeList) { episode ->
            episode?.let {
                EpisodesListRowView(episode)
            }
        }
    }
}

