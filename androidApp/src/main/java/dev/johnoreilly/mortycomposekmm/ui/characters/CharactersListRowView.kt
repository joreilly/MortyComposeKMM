package dev.johnoreilly.mortycomposekmm.ui.characters

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.johnoreilly.mortycomposekmm.fragment.CharacterDetail
import dev.johnoreilly.mortycomposekmm.ui.theme.getStatusColor

@Composable
fun CharactersListRowView(character: CharacterDetail, characterSelected: (network: CharacterDetail) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .shadow(
                elevation = 6.dp,
                shape = MaterialTheme.shapes.medium,
                spotColor = dev.johnoreilly.mortycomposekmm.ui.theme.PortalGreen.copy(alpha = 0.5f)
            ),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        onClick = { characterSelected(character) }
    ) {
        // Card content with portal-themed background gradient
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f)
                        )
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Character image with portal-themed border
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    dev.johnoreilly.mortycomposekmm.ui.theme.PortalGreen.copy(alpha = 0.7f),
                                    dev.johnoreilly.mortycomposekmm.ui.theme.PortalSwirl.copy(alpha = 0.3f)
                                )
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
                            .size(70.dp)
                            .clip(CircleShape)
                            .border(2.dp, MaterialTheme.colorScheme.surface, CircleShape)
                    )
                }

                // Character info
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    // Name
                    Text(
                        character.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    // Status with enhanced indicator
                    val statusColor = MaterialTheme.colorScheme.getStatusColor(character.status)
                    Card(
                        modifier = Modifier
                            .padding(top = 8.dp, bottom = 4.dp),
                        shape = MaterialTheme.shapes.small,
                        colors = CardDefaults.cardColors(
                            containerColor = statusColor.copy(alpha = 0.15f)
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(statusColor)
                            )
                            Text(
                                text = " ${character.status} - ${character.species}",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }
                    
                    // Location
                    Text(
                        "Last location: ${character.location?.name ?: "Unknown"}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    
                    // Episodes count with badge
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Text(
                            "Episodes:",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                        
                        Card(
                            modifier = Modifier.padding(start = 4.dp),
                            shape = MaterialTheme.shapes.small,
                            colors = CardDefaults.cardColors(
                                containerColor = dev.johnoreilly.mortycomposekmm.ui.theme.MeeseeksBlue.copy(alpha = 0.2f)
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                        ) {
                            Text(
                                text = "${character.episode.size}",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = dev.johnoreilly.mortycomposekmm.ui.theme.MeeseeksBlue,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}