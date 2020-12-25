package dev.johnoreilly.mortycomposekmm.ui.characters

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import dev.johnoreilly.mortycomposekmm.GetCharactersQuery
import org.koin.androidx.compose.getViewModel

@Composable
fun CharactersListView(bottmBar: @Composable () -> Unit, characterSelected: (network: GetCharactersQuery.Result) -> Unit) {
    val characterListsViewModel = getViewModel<CharacterListsViewModel>()
    val lazyCharacterList = characterListsViewModel.characters.collectAsLazyPagingItems()

    Scaffold(
        topBar = { TopAppBar(title = { Text( "Characters") }) },
        bottomBar = bottmBar)
    {
        LazyColumn {
            items(lazyCharacterList) { character ->
                character?.let {
                    CharactersListRowView(character, characterSelected)
                }
            }
        }
    }
}
