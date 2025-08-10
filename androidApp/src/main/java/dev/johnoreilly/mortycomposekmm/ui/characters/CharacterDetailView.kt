package dev.johnoreilly.mortycomposekmm.ui.characters

import android.annotation.SuppressLint
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.johnoreilly.mortycomposekmm.fragment.CharacterDetail
import dev.johnoreilly.mortycomposekmm.shared.viewmodel.CharactersViewModel
import dev.johnoreilly.mortycomposekmm.ui.components.MortyDetailTopAppBar
import dev.johnoreilly.mortycomposekmm.ui.components.PortalLoadingAnimation
import dev.johnoreilly.mortycomposekmm.ui.theme.getStatusColor
import org.koin.compose.koinInject

@Composable
fun CharacterDetailView(characterId: String, popBack: () -> Unit, updateTitle: (String) -> Unit) {
    val viewModel: CharactersViewModel = koinInject()
    val (character, setCharacter) = remember { mutableStateOf<CharacterDetail?>(null) }

    LaunchedEffect(characterId) {
        setCharacter(viewModel.getCharacter(characterId))
    }
    
    // Update the shared title when character is loaded
    LaunchedEffect(character) {
        character?.let {
            updateTitle(it.name)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        character?.let { char ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Character header with image and basic info
                item {
                    CharacterHeader(char)
                }
                
                // Character details section
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
                            // Information section with themed background
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                shape = MaterialTheme.shapes.small,
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)
                                ),
                                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    SectionTitle(
                                        title = "Information",
                                        icon = Icons.Default.Person
                                    )
                                    
                                    Spacer(modifier = Modifier.height(12.dp))
                                    
                                    InfoRow(label = "Status", value = char.status, highlight = true)
                                    InfoRow(label = "Species", value = char.species)
                                    if (char.type.isNotEmpty()) {
                                        InfoRow(label = "Type", value = char.type)
                                    }
                                    InfoRow(label = "Gender", value = char.gender)
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            // Location section with themed background
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                shape = MaterialTheme.shapes.small,
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.2f)
                                ),
                                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    SectionTitle(
                                        title = "Origin & Location",
                                        icon = Icons.Default.LocationOn,
                                        tint = MaterialTheme.colorScheme.tertiary
                                    )
                                    
                                    Spacer(modifier = Modifier.height(12.dp))
                                    
                                    InfoRow(label = "Origin", value = char.origin?.name ?: "Unknown")
                                    InfoRow(label = "Last known location", value = char.location?.name ?: "Unknown", highlight = true)
                                }
                            }
                        }
                    }
                }
                
                // Episodes section
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        shape = MaterialTheme.shapes.medium,
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            // Episodes header with count badge
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                SectionTitle(
                                    title = "Episodes",
                                    icon = Icons.Default.Tv,
                                    tint = dev.johnoreilly.mortycomposekmm.ui.theme.MeeseeksBlue
                                )
                                
                                Spacer(modifier = Modifier.weight(1f))
                                
                                // Episode count badge
                                Card(
                                    shape = MaterialTheme.shapes.small,
                                    colors = CardDefaults.cardColors(
                                        containerColor = dev.johnoreilly.mortycomposekmm.ui.theme.MeeseeksBlue.copy(alpha = 0.2f)
                                    )
                                ) {
                                    Text(
                                        text = "${char.episode.size}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = dev.johnoreilly.mortycomposekmm.ui.theme.MeeseeksBlue,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            // Episodes list with styled background
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = MaterialTheme.shapes.small,
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.1f)
                                ),
                                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                            ) {
                                CharacterEpisodeList(char)
                            }
                        }
                    }
                }
            }
        } ?: run {
            // Loading state with portal-themed animation
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
private fun CharacterHeader(character: CharacterDetail) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = androidx.compose.ui.graphics.Brush.radialGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                            MaterialTheme.colorScheme.surface
                        ),
                        radius = 800f,
                        center = androidx.compose.ui.geometry.Offset(0.5f, 0.5f)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Character image with portal-like border effect
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(240.dp)
                        .background(
                            brush = androidx.compose.ui.graphics.Brush.radialGradient(
                                colors = listOf(
                                    dev.johnoreilly.mortycomposekmm.ui.theme.PortalSwirl,
                                    dev.johnoreilly.mortycomposekmm.ui.theme.PortalGreen
                                ),
                                center = androidx.compose.ui.geometry.Offset(0.5f, 0.5f)
                            ),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = character.image,
                        contentDescription = character.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(210.dp)
                            .clip(CircleShape)
                            .border(5.dp, MaterialTheme.colorScheme.surface, CircleShape)
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Character name with enhanced styling
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    maxLines = 2,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                // Enhanced status indicator
                val statusColor = MaterialTheme.colorScheme.getStatusColor(character.status)
                Card(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .align(Alignment.CenterHorizontally)
                        .border(
                            width = 1.5f.dp,
                            color = statusColor,
                            shape = MaterialTheme.shapes.medium
                        ),
                    shape = MaterialTheme.shapes.medium,
                    colors = CardDefaults.cardColors(
                        containerColor = statusColor.copy(alpha = 0.15f)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .clip(CircleShape)
                                .background(statusColor)
                        )
                        Text(
                            text = " ${character.status} - ${character.species}",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionTitle(
    title: String, 
    icon: ImageVector,
    tint: Color = MaterialTheme.colorScheme.primary
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = tint
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = tint,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
private fun InfoRow(
    label: String, 
    value: String,
    highlight: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (highlight) FontWeight.Bold else FontWeight.Medium,
            color = if (highlight) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
    }
    Divider(
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
        modifier = Modifier.padding(vertical = 4.dp)
    )
}

@Composable
private fun CharacterEpisodeList(character: CharacterDetail) {
    Column(modifier = Modifier.padding(8.dp)) {
        character.episode.let { episodeList ->
            episodeList.filterNotNull().forEachIndexed { index, episode ->
                // Episode item with alternating background
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Episode number indicator
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${index + 1}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                    
                    // Episode details
                    Column(
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .weight(1f)
                    ) {
                        Text(
                            text = episode.name,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        
                        Spacer(modifier = Modifier.height(2.dp))
                        
                        Text(
                            text = episode.air_date,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                }
                
                // Don't add divider after the last item
                if (index < episodeList.size - 1) {
                    Divider(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                        modifier = Modifier.padding(start = 44.dp) // Align with text, not with the circle
                    )
                }
            }
        }
    }
}
