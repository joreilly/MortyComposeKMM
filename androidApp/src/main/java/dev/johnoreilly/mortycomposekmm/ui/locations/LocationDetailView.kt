package dev.johnoreilly.mortycomposekmm.ui.locations

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
import dev.johnoreilly.mortycomposekmm.fragment.LocationDetail
import dev.johnoreilly.mortycomposekmm.shared.viewmodel.LocationsViewModel
import dev.johnoreilly.mortycomposekmm.ui.components.MortyDetailTopAppBar
import dev.johnoreilly.mortycomposekmm.ui.components.PortalLoadingAnimation
import org.koin.compose.koinInject

@Composable
fun LocationDetailView(locationId: String, popBack: () -> Unit, updateTitle: (String) -> Unit) {
    val viewModel: LocationsViewModel = koinInject()
    val (location, setLocation) = remember { mutableStateOf<LocationDetail?>(null) }

    LaunchedEffect(locationId) {
        setLocation(viewModel.getLocation(locationId))
    }
    
    // Update the shared title when location is loaded
    LaunchedEffect(location) {
        location?.let {
            updateTitle(it.name)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        location?.let { loc ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Location header
                item {
                    LocationHeader(loc)
                }
                
                // Residents section
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
                                text = "Residents",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            
                            LocationResidentList(loc)
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
private fun LocationHeader(location: LocationDetail) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.7f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = location.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Type: ${location.type}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.8f)
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = "Dimension: ${location.dimension}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
private fun LocationResidentList(location: LocationDetail) {
    Column {
        location.residents.filterNotNull().forEach { resident ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Resident image
                AsyncImage(
                    model = resident.image,
                    contentDescription = resident.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                
                // Resident name
                Text(
                    text = resident.name,
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