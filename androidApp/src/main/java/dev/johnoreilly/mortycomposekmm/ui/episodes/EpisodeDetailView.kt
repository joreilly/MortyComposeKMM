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
import coil.transform.CircleCropTransformation
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.johnoreilly.mortycomposekmm.fragment.EpisodeDetail
import dev.johnoreilly.mortycomposekmm.ui.MainViewModel


@Composable
fun EpisodeDetailView(viewModel: MainViewModel, episodeId: String, popBack: () -> Unit) {
    val (episode, setEpisode) = remember { mutableStateOf<EpisodeDetail?>(null) }

    LaunchedEffect(episodeId) {
        setEpisode(viewModel.getEpisode(episodeId))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(episode?.name ?: "") },
                navigationIcon = {
                    IconButton(onClick = { popBack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        })
    {
        Surface(color = Color.LightGray) {

            LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
                // use `item` for separate elements like headers
                // and `items` for lists of identical elements
                item {
                    episode?.let {

                        Text(
                            "Characters",
                            style = MaterialTheme.typography.h5,
                            color = AmbientContentColor.current,
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                        )

                        Surface(color = Color.White) {
                            EpisodeCharactersList(episode)
                        }

                    }
                }
            }
        }
    }

}

@Composable
private fun EpisodeCharactersList(episode: EpisodeDetail) {

    Column(modifier = Modifier.padding(horizontal = 16.dp),) {
        episode.characters?.let { characterList ->
            characterList.filterNotNull().forEach { character ->
                Row(modifier = Modifier.padding(vertical = 8.dp)) {

                    Surface(
                        modifier = Modifier.preferredSize(28.dp),
                        shape = CircleShape,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
                    ) {
                        character.image?.let {
                            CoilImage(
                                data = it,
                                modifier = Modifier.preferredSize(28.dp),
                                requestBuilder = {
                                    transformations(CircleCropTransformation())
                                },
                                contentDescription = character.name
                            )
                        }
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

