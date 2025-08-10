import SwiftUI
import shared


struct CharactersListRowView: View {
    let character: CharacterDetail
    
    var body: some View {
        HStack(spacing: 16) {
            // Character image with circular frame
            ZStack {
                Circle()
                    .fill(LinearGradient(
                        gradient: Gradient(colors: [Color.green.opacity(0.7), Color.blue.opacity(0.3)]),
                        startPoint: .topLeading,
                        endPoint: .bottomTrailing
                    ))
                    .frame(width: 80, height: 80)
                
                AsyncImage(url: URL(string: character.image)) { image in
                    image.resizable()
                } placeholder: {
                    ProgressView()
                }
                .frame(width: 70, height: 70)
                .clipShape(Circle())
                .overlay(
                    Circle()
                        .stroke(Color.white, lineWidth: 2)
                )
            }
            
            // Character info
            VStack(alignment: .leading, spacing: 4) {
                Text(character.name)
                    .font(.title3)
                    .fontWeight(.bold)
                    .foregroundColor(.primary)
                    .lineLimit(1)
                
                // Status indicator
                HStack(spacing: 4) {
                    Circle()
                        .fill(character.status == "Alive" ? Color.green : 
                              character.status == "Dead" ? Color.red : Color.gray)
                        .frame(width: 8, height: 8)
                    Text("\(character.status) - \(character.species)")
                        .font(.caption)
                        .foregroundColor(.secondary)
                }
                .padding(.vertical, 4)
                
                // Location
                Text("Last location: \(character.location.name)")
                    .font(.caption)
                    .foregroundColor(.secondary)
                    .lineLimit(1)
                
                // Episodes count
                HStack {
                    Text("Episodes:")
                        .font(.caption)
                        .foregroundColor(.secondary)
                    
                    Text("\(character.episode.count)")
                        .font(.caption)
                        .fontWeight(.bold)
                        .foregroundColor(.blue)
                        .padding(.horizontal, 6)
                        .padding(.vertical, 2)
                        .background(Color.blue.opacity(0.2))
                        .cornerRadius(4)
                }
                .padding(.top, 4)
            }
        }
        .padding(.vertical, 8)
    }
}
