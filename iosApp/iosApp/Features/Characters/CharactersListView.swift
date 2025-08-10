import SwiftUI
import shared
import KMPObservableViewModelSwiftUI


struct CharactersListView: View {
    @StateViewModel var viewModel = CharactersViewModel()
    @State private var selectedCharacter: CharacterDetail? = nil
    
    var body: some View {
        List {
            ForEach(viewModel.charactersSnapshotList.indices, id: \.self) { index in
                if let character = viewModel.getElement(index: Int32(index)) {
                    NavigationLink(
                        destination: CharacterDetailView(character: character),
                        tag: character,
                        selection: $selectedCharacter
                    ) {
                        CharactersListRowView(character: character)
                            .onTapGesture {
                                selectedCharacter = character
                            }
                    }
                }
            }
        }
        .navigationTitle("Characters")
    }
}
