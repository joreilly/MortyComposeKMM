import SwiftUI

struct EpisodesListView: View {
    @StateObject private var data = EpisodeListViewModel()
    
    var body: some View {
        List {
            ForEach(data.episodes, id: \.id) { episode in
                EpisodesListRowView(episode: episode)
            }
            if data.shouldDisplayNextPage {
                nextPageView
            }
        }
        .navigationTitle("Episodes")
        .onAppear {
            data.fetchEpisodes()
        }
    }
    
    private var nextPageView: some View {
        HStack {
            Spacer()
            VStack {
                ProgressView()
                Text("Loading next page...")
            }
            Spacer()
        }
        .onAppear(perform: {
            data.fetchNextData()
        })
    }
}
