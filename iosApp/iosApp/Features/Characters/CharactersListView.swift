import SwiftUI
import shared
import KMPObservableViewModelSwiftUI


struct CharactersListView: View {
    @StateViewModel var viewModel = CharactersViewModel()
    
    var body: some View {
        List {
            ForEach(viewModel.charactersSnapshotList.indices, id: \.self) { index in
                if let character = viewModel.getElement(index: Int32(index)) {
                    CharactersListRowView(character: character)
                }
            }
        }
        .navigationTitle("Characters")
    }
}
