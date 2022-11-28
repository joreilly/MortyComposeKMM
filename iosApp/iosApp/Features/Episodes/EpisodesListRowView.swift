import SwiftUI
import shared


struct EpisodesListRowView: View {
    let episode: EpisodeDetail
    
    var body: some View {
        VStack(alignment: .leading) {
            Text(episode.name )
                .font(.title3)
                .foregroundColor(.accentColor)
            Text(episode.episode)
                .font(.footnote)
                .foregroundColor(.gray)
        }
    }
}
