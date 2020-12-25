package dev.johnoreilly.mortycomposekmm.ui.characters

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.johnoreilly.mortycomposekmm.GetCharacterQuery
import org.koin.androidx.compose.getViewModel

@Composable
fun CharacterDetailView(characterId: String, popBack: () -> Unit) {
    val characterListsViewModel = getViewModel<CharacterListsViewModel>()
    val (character, setCharacter) = remember { mutableStateOf<GetCharacterQuery.Character?>(null) }

    LaunchedEffect(characterId) {
        setCharacter(characterListsViewModel.getCharacter(characterId))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(character?.name ?: "") },
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
                character?.let {

                    Text("Mugshot", style = typography.h5, color = Color.Gray,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp))

                    Surface(color = Color.White) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            val imageUrl = character.image
                            if (imageUrl != null) {
                                Card(
                                    modifier = Modifier.preferredSize(150.dp),
                                    shape = RoundedCornerShape(25.dp)
                                ) {
                                    CoilImage(data = imageUrl)
                                }
                            }
                        }
                    }


                    Text("Episodes", style = typography.h5, color = Color.Gray,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp))

                    Surface(color = Color.White) {
                        CharacterEpisodeList(character)
                    }

                }

            }

        }

    }

}

@Composable
private fun CharacterEpisodeList(character: GetCharacterQuery.Character) {

    Column(modifier = Modifier.padding(horizontal = 16.dp),) {
        character.episode?.let { episodeList ->
            LazyColumn {
                items(episodeList) { episode ->
                    episode?.let {
                        Column {
                            Text(episode.name ?: "",
                                style = typography.h6)
                            Text(episode.air_date ?: "",
                                style = typography.subtitle2,
                                color = Color.Gray)
                        }
                        Divider()
                    }
                }
            }
        }
    }
}

