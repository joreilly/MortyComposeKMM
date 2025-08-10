package dev.johnoreilly.mortycomposekmm.ui.episodes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.johnoreilly.mortycomposekmm.fragment.EpisodeDetail
import dev.johnoreilly.mortycomposekmm.shared.viewmodel.EpisodesViewModel
import dev.johnoreilly.mortycomposekmm.ui.components.MortyDetailTopAppBar
import dev.johnoreilly.mortycomposekmm.ui.components.PortalLoadingAnimation
import org.koin.compose.koinInject

@Composable
fun EpisodeDetailView(episodeId: String, popBack: () -> Unit, updateTitle: (String) -> Unit) {
    val viewModel: EpisodesViewModel = koinInject()
    val (episode, setEpisode) = remember { mutableStateOf<EpisodeDetail?>(null) }

    LaunchedEffect(episodeId) {
        setEpisode(viewModel.getEpisode(episodeId))
    }
    
    // Update the shared title when episode is loaded
    LaunchedEffect(episode) {
        episode?.let {
            updateTitle(it.name)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        episode?.let { ep ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Episode header
                item {
                    EpisodeHeader(ep)
                }
                
                // Characters section
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        shape = MaterialTheme.shapes.medium,
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Characters",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            
                            EpisodeCharactersList(ep)
                        }
                    }
                }
            }
        } ?: run {
            // Loading state
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                PortalLoadingAnimation()
            }
        }
    }
}

@Composable
private fun EpisodeHeader(episode: EpisodeDetail) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = episode.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Episode: ${episode.episode}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = "Air Date: ${episode.air_date}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
private fun EpisodeCharactersList(episode: EpisodeDetail) {
    Column {
        episode.characters.filterNotNull().forEach { character ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Character image
                AsyncImage(
                    model = character.image,
                    contentDescription = character.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                
                // Character name
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
            Divider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}