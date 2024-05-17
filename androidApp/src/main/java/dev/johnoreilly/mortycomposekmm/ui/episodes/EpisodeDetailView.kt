package dev.johnoreilly.mortycomposekmm.ui.episodes

import android.annotation.SuppressLint
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.johnoreilly.mortycomposekmm.fragment.EpisodeDetail
import dev.johnoreilly.mortycomposekmm.shared.viewmodel.EpisodesViewModel
import org.koin.compose.koinInject


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EpisodeDetailView(episodeId: String, popBack: () -> Unit) {
    val viewModel: EpisodesViewModel = koinInject()
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

                episode?.let {

                    item(episode.id) {

                        Text(
                            "Characters",
                            style = MaterialTheme.typography.h5,
                            color = LocalContentColor.current,
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

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        episode.characters.filterNotNull().forEach { character ->
            Row(modifier = Modifier.padding(vertical = 8.dp)) {

                AsyncImage(
                    model = character.image,
                    contentDescription = character.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(28.dp).clip(CircleShape)
                )

                Text(
                    character.name,
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                    style = MaterialTheme.typography.h6
                )
            }
            Divider()
        }
    }
}

