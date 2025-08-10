import SwiftUI
import shared
import KMPObservableViewModelSwiftUI

struct EpisodesListView: View {
    @StateViewModel var viewModel = EpisodesViewModel()
    @State private var selectedEpisode: EpisodeDetail? = nil
    
    var body: some View {
        List {
            ForEach(viewModel.episodesSnapshotList.indices, id: \.self) { index in
                if let episode = viewModel.getElement(index: Int32(index)) {
                    NavigationLink(
                        destination: EpisodeDetailView(episode: episode),
                        tag: episode,
                        selection: $selectedEpisode
                    ) {
                        EpisodesListRowView(episode: episode)
                            .onTapGesture {
                                selectedEpisode = episode
                            }
                    }
                }
            }
        }
        .navigationTitle("Episodes")
    }
}

struct EpisodeDetailView: View {
    let episode: EpisodeDetail
    
    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 16) {
                // Episode info
                VStack(alignment: .leading, spacing: 8) {
                    Text(episode.name)
                        .font(.title)
                        .fontWeight(.bold)
                    
                    Text(episode.episode)
                        .font(.headline)
                        .foregroundColor(.secondary)
                    
                    Text("Air date: \(episode.air_date)")
                        .font(.subheadline)
                }
                .padding(.horizontal)
                
                Divider()
                
                // Characters
                VStack(alignment: .leading, spacing: 8) {
                    Text("Characters:")
                        .font(.headline)
                    
                    Text("This episode features \(episode.characters.count) character(s)")
                        .font(.subheadline)
                }
                .padding(.horizontal)
                
                Spacer()
            }
            .padding(.vertical)
        }
        .navigationTitle(episode.name)
    }
}
