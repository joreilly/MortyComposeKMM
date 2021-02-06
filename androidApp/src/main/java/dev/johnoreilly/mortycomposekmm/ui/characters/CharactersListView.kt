package dev.johnoreilly.mortycomposekmm.ui.characters

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import dev.johnoreilly.mortycomposekmm.fragment.CharacterDetail
import org.koin.androidx.compose.getViewModel


@Composable
fun CharactersListView(bottomBar: @Composable () -> Unit, characterSelected: (character: CharacterDetail) -> Unit) {
    val characterListsViewModel = getViewModel<CharacterListsViewModel>()
    val lazyCharacterList = characterListsViewModel.characters.collectAsLazyPagingItems()


    Scaffold(
        topBar = { TopAppBar(title = { Text( "Characters") }) },
        bottomBar = bottomBar)
    {
        LazyColumn(contentPadding = it) {
            items(lazyCharacterList) { character ->
                character?.let {
                    CharactersListRowView(character, characterSelected)
                }
            }
        }
    }
}
