package dev.johnoreilly.mortycomposekmm.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Tv
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import dev.johnoreilly.mortycomposekmm.ui.characters.CharacterDetailView
import dev.johnoreilly.mortycomposekmm.ui.characters.CharactersListView
import dev.johnoreilly.mortycomposekmm.ui.episodes.EpisodeDetailView
import dev.johnoreilly.mortycomposekmm.ui.episodes.EpisodesListView
import dev.johnoreilly.mortycomposekmm.ui.locations.LocationDetailView
import dev.johnoreilly.mortycomposekmm.ui.locations.LocationsListView
import org.koin.androidx.compose.getViewModel


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

        setContent {
            MaterialTheme {
                MainLayout()
            }
        }
    }
}

@Composable
fun MainLayout() {
    val navController = rememberNavController()

    val bottomNavigationItems = listOf(Screens.CharactersScreen, Screens.EpisodesScreen, Screens.LocationsScreen)

    Scaffold(
        topBar = { TopAppBar(title = { Text( "Characters") }) },
        bottomBar = { MortyBottomNavigation(navController, bottomNavigationItems) })
    {

        NavHost(navController, startDestination = Screens.CharactersScreen.route, modifier = Modifier.padding(it).fillMaxSize()) {
            composable(Screens.CharactersScreen.route) {
                CharactersListView() {
                    navController.navigate(Screens.CharacterDetailsScreen.route + "/${it.id}")
                }
            }
            composable(Screens.CharacterDetailsScreen.route + "/{id}") { backStackEntry ->
                CharacterDetailView(
                    backStackEntry.arguments?.getString("id") as String,
                    popBack = { navController.popBackStack() })
            }
            composable(Screens.EpisodesScreen.route) {
                EpisodesListView() {
                    navController.navigate(Screens.EpisodeDetailsScreen.route + "/${it.id}")
                }
            }
            composable(Screens.EpisodeDetailsScreen.route + "/{id}") { backStackEntry ->
                EpisodeDetailView(
                    backStackEntry.arguments?.getString("id") as String,
                    popBack = { navController.popBackStack() })
            }
            composable(Screens.LocationsScreen.route) {
                LocationsListView() {
                    navController.navigate(Screens.LocationDetailsScreen.route + "/${it.id}")
                }
            }
            composable(Screens.LocationDetailsScreen.route + "/{id}") { backStackEntry ->
                LocationDetailView(
                    backStackEntry.arguments?.getString("id") as String,
                    popBack = { navController.popBackStack() })
            }
        }
    }
}


@Composable
private fun MortyBottomNavigation(
    navController: NavHostController,
    items: List<Screens>
) {
    BottomNavigation {
        val currentRoute = currentRoute(navController)
        items.forEach { screen ->
            BottomNavigationItem(
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

