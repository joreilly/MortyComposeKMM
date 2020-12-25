package dev.johnoreilly.mortycomposekmm.ui.episodes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.johnoreilly.mortycomposekmm.GetEpisodeQuery
import org.koin.androidx.compose.getViewModel


@Composable
fun EpisodeDetailView(episodeId: String, popBack: () -> Unit) {
    val episodesListsViewModel = getViewModel<EpisodesListViewModel>()
    val (episode, setEpisode) = remember { mutableStateOf<GetEpisodeQuery.Episode?>(null) }

    LaunchedEffect(episodeId) {
        setEpisode(episodesListsViewModel.getEpisode(episodeId))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(episode?.name ?: "") },
                navigationIcon = {
                    IconButton(onClick = { popBack() }) {
                        Icon(Icons.Filled.ArrowBack)
                    }
                }
            )
        })
    {
        Surface(color = Color.LightGray) {

            Column(modifier = Modifier.padding(top = 16.dp)) {
                episode?.let {

                    Text("Characters", style = MaterialTheme.typography.h5, color = Color.Gray,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp))

                    Surface(color = Color.White) {
                        EpisodeCharactersList(episode)
                    }

                }

            }

        }

    }

}

@Composable
private fun EpisodeCharactersList(episode: GetEpisodeQuery.Episode) {

    Column(modifier = Modifier.padding(horizontal = 16.dp),) {
        episode.characters?.let { characterList ->
            LazyColumn {
                items(characterList) { character ->
                    character?.let {
                        Row(modifier = Modifier.padding(vertical = 8.dp)) {

                            val imageUrl = character.image
                            if (imageUrl != null) {
                                Card(modifier = Modifier.preferredSize(28.dp), shape = CircleShape) {
                                    CoilImage(data = imageUrl)
                                }
                            } else {
                                Spacer(modifier = Modifier.preferredSize(28.dp))
                            }

                            Text(character.name ?: "",
                                modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                                style = MaterialTheme.typography.h6)
                        }
                        Divider()
                    }
                }
            }
        }
    }
}

