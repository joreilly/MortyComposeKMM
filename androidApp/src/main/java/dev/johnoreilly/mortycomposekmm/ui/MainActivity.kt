package dev.johnoreilly.mortycomposekmm.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import dev.johnoreilly.mortycomposekmm.ui.characters.CharacterDetailView
import dev.johnoreilly.mortycomposekmm.ui.characters.CharactersListView
import dev.johnoreilly.mortycomposekmm.ui.components.MortyTopAppBar
import dev.johnoreilly.mortycomposekmm.ui.components.MortyDetailTopAppBar
import dev.johnoreilly.mortycomposekmm.ui.episodes.EpisodeDetailView
import dev.johnoreilly.mortycomposekmm.ui.episodes.EpisodesListView
import dev.johnoreilly.mortycomposekmm.ui.locations.LocationDetailView
import dev.johnoreilly.mortycomposekmm.ui.locations.LocationsListView
import dev.johnoreilly.mortycomposekmm.ui.theme.MortyComposeTheme


sealed class Screens(val route: String, val label: String, val icon: ImageVector? = null) {
    data object CharactersScreen : Screens("Characters", "Characters", Icons.Default.Person)
    data object EpisodesScreen : Screens("Episodes", "Episodes",  Icons.Default.Tv)
    data object LocationsScreen : Screens("Locations", "Locations",  Icons.Default.LocationOn)
    data object CharacterDetailsScreen : Screens("CharacterDetails", "CharacterDetails")
    data object EpisodeDetailsScreen : Screens("EpisodeDetails", "EpisodeDetails")
    data object LocationDetailsScreen : Screens("LocationDetails", "LocationDetails")
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            MortyComposeTheme {
                MainLayout()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLayout() {
    val navController = rememberNavController()
    val currentRoute = currentRoute(navController)
    
    // State to hold the detail screen title
    var detailTitle by remember { mutableStateOf("") }
    
    // Function to update the detail title (will be passed to detail screens)
    val updateDetailTitle: (String) -> Unit = { title ->
        detailTitle = title
    }
    
    // Determine if we're on a detail screen
    val isDetailScreen = remember(currentRoute) {
        currentRoute?.startsWith(Screens.CharacterDetailsScreen.route) == true ||
        currentRoute?.startsWith(Screens.EpisodeDetailsScreen.route) == true ||
        currentRoute?.startsWith(Screens.LocationDetailsScreen.route) == true
    }
    
    // Get current main screen based on route
    val currentMainScreen = remember(currentRoute) {
        when (currentRoute) {
            Screens.CharactersScreen.route -> Screens.CharactersScreen
            Screens.EpisodesScreen.route -> Screens.EpisodesScreen
            Screens.LocationsScreen.route -> Screens.LocationsScreen
            else -> Screens.CharactersScreen
        }
    }

    val bottomNavigationItems = listOf(Screens.CharactersScreen, Screens.EpisodesScreen, Screens.LocationsScreen)

    Scaffold(
        // Use our shared MortyTopAppBar component with dynamic content
        topBar = {
            if (isDetailScreen) {
                // Use detail app bar for detail screens
                MortyDetailTopAppBar(
                    title = detailTitle,
                    onBackClick = { navController.popBackStack() }
                )
            } else {
                // Use regular app bar for main screens
                MortyTopAppBar(
                    title = currentMainScreen.label
                )
            }
        },
        // Apply navigation bar padding to the bottom navigation
        bottomBar = { 
            Surface(
                tonalElevation = 8.dp,
                color = MaterialTheme.colorScheme.surface,
            ) {
                MortyBottomNavigation(navController, bottomNavigationItems)
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->

        NavHost(navController, startDestination = Screens.CharactersScreen.route, 
                modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            composable(Screens.CharactersScreen.route) {
                CharactersListView() {
                    navController.navigate(Screens.CharacterDetailsScreen.route + "/${it.id}")
                }
            }
            composable(Screens.CharacterDetailsScreen.route + "/{id}") { backStackEntry ->
                CharacterDetailView(
                    characterId = backStackEntry.arguments?.getString("id") as String,
                    popBack = { navController.popBackStack() },
                    updateTitle = updateDetailTitle
                )
            }
            composable(Screens.EpisodesScreen.route) {
                EpisodesListView() {
                    navController.navigate(Screens.EpisodeDetailsScreen.route + "/${it.id}")
                }
            }
            composable(Screens.EpisodeDetailsScreen.route + "/{id}") { backStackEntry ->
                EpisodeDetailView(
                    episodeId = backStackEntry.arguments?.getString("id") as String,
                    popBack = { navController.popBackStack() },
                    updateTitle = updateDetailTitle
                )
            }
            composable(Screens.LocationsScreen.route) {
                LocationsListView() {
                    navController.navigate(Screens.LocationDetailsScreen.route + "/${it.id}")
                }
            }
            composable(Screens.LocationDetailsScreen.route + "/{id}") { backStackEntry ->
                LocationDetailView(
                    locationId = backStackEntry.arguments?.getString("id") as String,
                    popBack = { navController.popBackStack() },
                    updateTitle = updateDetailTitle
                )
            }
        }
    }
}


@Composable
private fun MortyBottomNavigation(
    navController: NavHostController,
    items: List<Screens>
) {
    NavigationBar {
        val currentRoute = currentRoute(navController)
        items.forEach { screen ->
            NavigationBarItem(
                icon = { screen.icon?.let { Icon(screen.icon, contentDescription = screen.label) } },
                label = { Text(screen.label) },
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }
}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}