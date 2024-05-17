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
import dev.johnoreilly.mortycomposekmm.shared.viewmodel.EpisodesViewModel
import org.koin.compose.koinInject


@Composable
fun EpisodesListView(episodeSelected: (episode: EpisodeDetail) -> Unit) {
    val viewModel: EpisodesViewModel = koinInject()
    val lazyEpisodeList = viewModel.episodesFlow.collectAsLazyPagingItems()

    LazyColumn {
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

