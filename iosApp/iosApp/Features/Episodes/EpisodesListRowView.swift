import SwiftUI
import shared


struct EpisodesListRowView: View {
    let episode: EpisodeDetail
    
    var body: some View {
        VStack(alignment: .leading, spacing: 4) {
            HStack {
                VStack(alignment: .leading, spacing: 4) {
                    Text(episode.name)
                        .font(.title3)
                        .fontWeight(.bold)
                        .foregroundColor(.primary)
                        .lineLimit(1)
                    
                    Text(episode.episode)
                        .font(.subheadline)
                        .foregroundColor(.secondary)
                }
                
                Spacer()
                
                Text(episode.air_date)
                    .font(.caption)
                    .foregroundColor(.secondary)
                    .padding(.leading, 8)
            }
            
            Divider()
                .padding(.top, 4)
        }
        .padding(.vertical, 4)
    }
}
