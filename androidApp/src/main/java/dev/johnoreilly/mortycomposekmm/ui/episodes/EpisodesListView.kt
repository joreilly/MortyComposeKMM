package dev.johnoreilly.mortycomposekmm.ui.episodes

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import dev.johnoreilly.mortycomposekmm.fragment.EpisodeDetail
import dev.johnoreilly.mortycomposekmm.ui.MainViewModel


@Composable
fun EpisodesListView(viewModel: MainViewModel, bottomBar: @Composable () -> Unit, episodeSelected: (episode: EpisodeDetail) -> Unit) {
    val lazyEpisodeList = viewModel.episodes.collectAsLazyPagingItems()

    Scaffold(
        topBar = { TopAppBar(title = { Text( "Episodes") }) },
        bottomBar = bottomBar)
    {
        LazyColumn(contentPadding = it) {
            items(
                count = lazyEpisodeList.itemCount,
                key = lazyEpisodeList.itemKey { it.id },
                contentType = lazyEpisodeList.itemContentType { "MyPagingItems" }
            ) { index ->
                val episode = lazyEpisodeList[index]
                episode?.let {
                    EpisodesListRowView(episode, episodeSelected)
                }
            }
        }
    }
}

