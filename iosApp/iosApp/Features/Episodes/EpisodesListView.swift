import SwiftUI

struct EpisodesListView: View {
    @StateObject private var viewModel = EpisodeListViewModel()
    
    var body: some View {
        List {
            ForEach(viewModel.episodes.indices, id: \.self) { index in
                let episode = viewModel.getElement(index: index)
                if let episode {
                    EpisodesListRowView(episode: episode)
                }
            }
        }
        .navigationTitle("Episodes")
        .task {
            await viewModel.fetchEpisodes()
        }
    }
}
