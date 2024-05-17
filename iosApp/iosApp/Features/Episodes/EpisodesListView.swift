import SwiftUI
import shared
import KMPObservableViewModelSwiftUI

struct EpisodesListView: View {
    @StateViewModel var viewModel = EpisodesViewModel()
    
    var body: some View {
        List {
            ForEach(viewModel.episodesSnapshotList.indices, id: \.self) { index in
                if let episode = viewModel.getElement(index: Int32(index)) {
                    EpisodesListRowView(episode: episode)
                }
            }
        }
        .navigationTitle("Episodes")
    }
}
