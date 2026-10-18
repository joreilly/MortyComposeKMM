import SwiftUI
import Shared


struct CharactersListView: View {
    @State private var viewModel = shared.viewmodel.CharactersViewModel()
    @State private var selectedCharacter: CharacterDetail? = nil
    
    var body: some View {
        Observing(viewModel.charactersLoadError) { error in
            if let error {
                LoadErrorView(message: error) { viewModel.retry() }
            } else {
                Observing(viewModel.charactersSnapshotList) { snapshotList in
                    List {
                        ForEach(snapshotList.indices, id: \.self) { index in
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
                }
            }
        }
        .navigationTitle("Characters")
    }
}
