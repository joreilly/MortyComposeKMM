package dev.johnoreilly.mortycomposekmm.ui.characters

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import dev.johnoreilly.mortycomposekmm.fragment.CharacterDetail
import dev.johnoreilly.mortycomposekmm.shared.viewmodel.CharactersViewModel
import dev.johnoreilly.mortycomposekmm.ui.components.PortalErrorScreen
import dev.johnoreilly.mortycomposekmm.ui.components.PortalLoadingAnimation
import dev.johnoreilly.mortycomposekmm.ui.components.SmallErrorIndicator
import dev.johnoreilly.mortycomposekmm.ui.components.SmallPortalLoadingAnimation
import org.koin.compose.koinInject


@Composable
fun CharactersListView(characterSelected: (character: CharacterDetail) -> Unit) {
    val viewModel: CharactersViewModel = koinInject()
    val lazyCharacterList = viewModel.charactersFlow.collectAsLazyPagingItems()

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
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
            
            // Add loading footer when more items are being loaded
            item {
                when (lazyCharacterList.loadState.append) {
                    is LoadState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            SmallPortalLoadingAnimation()
                        }
                    }
                    is LoadState.Error -> {
                        SmallErrorIndicator(
                            message = "Could not load more characters",
                            onRetry = { lazyCharacterList.retry() }
                        )
                    }
                    else -> {}
                }
            }
        }
        
        // Show loading state for initial load
        when (lazyCharacterList.loadState.refresh) {
            is LoadState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    PortalLoadingAnimation()
                }
            }
            is LoadState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    PortalErrorScreen(
                        message = "Could not load characters",
                        onRetry = { lazyCharacterList.retry() }
                    )
                }
            }
            else -> {}
        }
    }
}
