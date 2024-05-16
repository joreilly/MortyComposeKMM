import SwiftUI

struct CharactersListView: View {
    @StateObject private var viewModel = CharacterListViewModel()
    
    var body: some View {
        List {
            ForEach(viewModel.characters.indices, id: \.self) { index in
                let character = viewModel.getElement(index: index)
                if let character {
                    CharactersListRowView(character: character)
                }
            }
        }
        .navigationTitle("Characters")
        .task {
            await viewModel.fetchCharacters()
        }
    }
}
