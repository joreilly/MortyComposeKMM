package dev.johnoreilly.mortycomposekmm.ui.characters

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import dev.johnoreilly.mortycomposekmm.fragment.CharacterDetail
import dev.johnoreilly.mortycomposekmm.ui.MainViewModel


@Composable
fun CharactersListView(viewModel: MainViewModel, bottomBar: @Composable () -> Unit, characterSelected: (character: CharacterDetail) -> Unit) {
    val lazyCharacterList = viewModel.characters.collectAsLazyPagingItems()


    Scaffold(
        topBar = { TopAppBar(title = { Text( "Characters") }) },
        bottomBar = bottomBar)
    {
        LazyColumn(contentPadding = it) {

            items(
                count = lazyCharacterList.itemCount,
                key = lazyCharacterList.itemKey { it.id },
                contentType = lazyCharacterList.itemContentType { "MyPagingItems" }
            ) { index ->
                val character = lazyCharacterList[index]
                character?.let {
                    CharactersListRowView(character, characterSelected)
                }
            }
        }
    }
}
