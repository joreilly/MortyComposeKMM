# MortyComposeKMM

Rick & Morty app to demonstrate use of GraphQL + Jetpack Compose (heavily based on https://github.com/Dimillian/MortyUI).
This is also a Kotlin Multiplatform project with GraphQL code in shared module (making use of [Apollo library's Kotlin Multiplatform support](https://www.apollographql.com/docs/android/essentials/get-started-multiplatform/)).


Related Posts:
* [Jetpack Compose and GraphQL, a very merry combination!](https://johnoreilly.dev/posts/jetpack-compose-graphql/)


![Characters Android Screenshot](/art/characters_screenshot.png?raw=true)


The project also makes use of Jetpack Compose's [Paging library](https://developer.android.com/jetpack/androidx/releases/paging#paging_compose_version_100_2)
that allows setting up `LazyColumn` for example that's driven from `PagingSource` as shown below (that source in our case invokes Apollo GraphQL queries). (UPDATE: have started to use [multiplatform-paging](https://github.com/kuuuurt/multiplatform-paging) library for managing paging within the Kotlin Multiplatform shared code).

```kotlin
class CharacterListsViewModel(private val repository: MortyRepository): ViewModel() {
    
    val characters: Flow<PagingData<CharacterDetail>> = Pager(PagingConfig(pageSize = 20)) {
        CharactersDataSource(repository)
    }.flow

}

@Composable
fun CharactersListView() {
    val characterListsViewModel = getViewModel<CharacterListsViewModel>()
    val lazyCharacterList = characterListsViewModel.characters.collectAsLazyPagingItems()

    LazyColumn {
        items(lazyCharacterList) { character ->
            character?.let {
                CharactersListRowView(character)
            }
        }
    }
}
```


## iOS App

A small SwiftUI iOS app that uses same shared Kotlin Multiplatform code is in the `iosApp` folder
 (shows Characters screen using more or less same SwiftUI code that's in https://github.com/Dimillian/MortyUI)

![Characters iOS Screenshot](/art/characters_screenshot_ios.png?raw=true)