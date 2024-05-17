package dev.johnoreilly.mortycomposekmm.ui.characters

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import dev.johnoreilly.mortycomposekmm.fragment.CharacterDetail
import dev.johnoreilly.mortycomposekmm.shared.viewmodel.CharactersViewModel
import org.koin.compose.koinInject


@Composable
fun CharactersListView(characterSelected: (character: CharacterDetail) -> Unit) {
    val viewModel: CharactersViewModel = koinInject()
    val lazyCharacterList = viewModel.charactersFlow.collectAsLazyPagingItems()

    LazyColumn {
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
