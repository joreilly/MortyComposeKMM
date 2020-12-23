package dev.johnoreilly.mortycomposekmm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import dev.johnoreilly.mortycomposekmm.ui.characters.CharactersListView
import dev.johnoreilly.mortycomposekmm.ui.episodes.EpisodesListView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                MainLayout()
            }
        }
    }
}

sealed class BottomNavigationScreens(val route: String, val label: String, val icon: ImageVector) {
    object CharactersScreen : BottomNavigationScreens("Characters", "Characters", Icons.Default.Person)
    object EpisodesScreen : BottomNavigationScreens("Episodes", "Episodes", Icons.Default.Face)
}

@Composable
fun MainLayout() {
    val navController = rememberNavController()
    var title by remember { mutableStateOf("") }

    val bottomNavigationItems = listOf(
        BottomNavigationScreens.CharactersScreen,
        BottomNavigationScreens.EpisodesScreen
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(title) })
        },
        bodyContent = {
            NavHost(navController, startDestination = BottomNavigationScreens.CharactersScreen.route) {
                composable(BottomNavigationScreens.CharactersScreen.route) {
                    title = BottomNavigationScreens.CharactersScreen.label
                    CharactersListView()
                }
                composable(BottomNavigationScreens.EpisodesScreen.route) {
                    title = BottomNavigationScreens.EpisodesScreen.label
                    EpisodesListView()
                }
            }
        },
        bottomBar = {
            BottomNavigation {
                val currentRoute = currentRoute(navController)
                bottomNavigationItems.forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(screen.icon) },
                        label = { Text(screen.label) },
                        selected = currentRoute == screen.route,
                        alwaysShowLabels = false, // This hides the title for the unselected items
                        onClick = {
                            // This if check gives us a "singleTop" behavior where we do not create a
                            // second instance of the composable if we are already on that destination
                            if (currentRoute != screen.route) {
                                navController.navigate(screen.route)
                            }
                        }
                    )
                }
            }
        }
    )
}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.arguments?.getString(KEY_ROUTE)
}

