import SwiftUI
import shared

struct CharacterDetailView: View {
    let character: CharacterDetail
    
    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 16) {
                // Character header with image and basic info
                CharacterHeader(character: character)
                
                // Character details section
                CharacterDetailsSection(character: character)
                
                // Episodes section
                VStack(alignment: .leading, spacing: 16) {
                    EpisodesSection(episodes: character.episode as! [CharacterDetail.Episode?])
                }
                .padding(.horizontal)
            }
            .padding(.vertical)
        }
        .navigationTitle(character.name)
    }
}

struct CharacterDetailsSection: View {
    let character: CharacterDetail
    
    var body: some View {
        VStack(spacing: 16) {
            // Information section
            InformationSection(character: character)
            
            // Location section
            LocationSection(origin: character.origin.name, location: character.location.name)
        }
        .padding(.horizontal)
    }
}

struct InformationSection: View {
    let character: CharacterDetail
    
    var body: some View {
        CardSection {
            SectionTitle(title: "Information", systemName: "person.fill", tint: .blue)
            
            VStack(spacing: 8) {
                InfoRow(label: "Status", value: character.status, highlight: true)
                InfoRow(label: "Species", value: character.species)
                if !character.type.isEmpty {
                    InfoRow(label: "Type", value: character.type)
                }
                InfoRow(label: "Gender", value: character.gender)
            }
            .padding(.top, 12)
        }
        .background(Color.blue.opacity(0.1))
    }
}

struct LocationSection: View {
    let origin: String
    let location: String
    
    var body: some View {
        CardSection {
            SectionTitle(title: "Origin & Location", systemName: "location.fill", tint: .green)
            
            VStack(spacing: 8) {
                InfoRow(label: "Origin", value: origin)
                InfoRow(label: "Last known location", value: location, highlight: true)
            }
            .padding(.top, 12)
        }
        .background(Color.green.opacity(0.1))
    }
}

// MARK: - Helper Components

struct CharacterHeader: View {
    let character: CharacterDetail
    
    var body: some View {
        ZStack {
            // Background gradient
            HeaderBackground()
            
            VStack(spacing: 16) {
                // Character image with portal-like border
                CharacterPortraitView(imageUrl: character.image)
                
                // Character name
                Text(character.name)
                    .font(.system(size: 24, weight: .bold))
                    .multilineTextAlignment(.center)
                    .lineLimit(2)
                
                // Status indicator
                StatusIndicator(status: character.status, species: character.species)
            }
            .padding(16)
        }
        .clipShape(RoundedRectangle(cornerRadius: 16))
        .padding(.horizontal)
    }
}

struct HeaderBackground: View {
    var body: some View {
        RadialGradient(
            gradient: Gradient(colors: [Color.blue.opacity(0.7), Color.white]),
            center: .center,
            startRadius: 5,
            endRadius: 300
        )
    }
}

struct CharacterPortraitView: View {
    let imageUrl: String
    
    var body: some View {
        ZStack {
            // Portal-like background
            PortalBackground()
            
            // Character image
            AsyncImage(url: URL(string: imageUrl)) { image in
                image
                    .resizable()
                    .aspectRatio(contentMode: .fill)
            } placeholder: {
                ProgressView()
            }
            .frame(width: 210, height: 210)
            .clipShape(Circle())
            .overlay(
                Circle()
                    .stroke(Color.white, lineWidth: 5)
            )
        }
    }
}

struct PortalBackground: View {
    var body: some View {
        RadialGradient(
            gradient: Gradient(colors: [Color.green, Color.green.opacity(0.5)]),
            center: .center,
            startRadius: 5,
            endRadius: 120
        )
        .clipShape(Circle())
        .frame(width: 240, height: 240)
    }
}

struct StatusIndicator: View {
    let status: String
    let species: String
    
    var body: some View {
        HStack {
            Circle()
                .fill(statusColor)
                .frame(width: 10, height: 10)
            
            Text("\(status) - \(species)")
                .font(.system(size: 16, weight: .medium))
        }
        .padding(.horizontal, 16)
        .padding(.vertical, 10)
        .background(statusColor.opacity(0.15))
        .overlay(
            RoundedRectangle(cornerRadius: 8)
                .stroke(statusColor, lineWidth: 1.5)
        )
        .clipShape(RoundedRectangle(cornerRadius: 8))
    }
    
    // Computed property for status color
    private var statusColor: Color {
        switch status {
        case "Alive":
            return .green
        case "Dead":
            return .red
        default:
            return .gray
        }
    }
}

struct SectionTitle: View {
    let title: String
    let systemName: String
    let tint: Color
    
    var body: some View {
        HStack {
            Image(systemName: systemName)
                .foregroundColor(tint)
            
            Text(title)
                .font(.system(size: 20, weight: .bold))
                .foregroundColor(tint)
        }
    }
}

struct InfoRow: View {
    let label: String
    let value: String
    var highlight: Bool = false
    
    var body: some View {
        VStack(spacing: 4) {
            HStack {
                Text(label)
                    .font(.system(size: 16))
                    .foregroundColor(.secondary)
                
                Spacer()
                
                Text(value)
                    .font(.system(size: 16, weight: highlight ? .bold : .medium))
                    .foregroundColor(highlight ? .blue : .primary)
            }
            
            Divider()
        }
    }
}

struct CardSection<Content: View>: View {
    let content: Content
    
    init(@ViewBuilder content: () -> Content) {
        self.content = content()
    }
    
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            content
        }
        .padding(16)
        .background(Color.white)
        .clipShape(RoundedRectangle(cornerRadius: 12))
        .shadow(color: Color.black.opacity(0.1), radius: 4, x: 0, y: 2)
    }
}

struct EpisodesSection: View {
    let episodes: [CharacterDetail.Episode?]
    
    var body: some View {
        CardSection {
            // Header with title and count
            EpisodesSectionHeader(count: episodes.count)
            
            // Episodes list
            EpisodesList(episodes: episodes)
        }
        .background(Color.purple.opacity(0.05))
    }
}

struct EpisodesSectionHeader: View {
    let count: Int
    
    var body: some View {
        HStack {
            SectionTitle(title: "Episodes", systemName: "tv.fill", tint: .purple)
            
            Spacer()
            
            // Episode count badge
            Text("\(count)")
                .font(.system(size: 14, weight: .bold))
                .padding(.horizontal, 8)
                .padding(.vertical, 4)
                .background(Color.purple.opacity(0.2))
                .foregroundColor(.purple)
                .clipShape(RoundedRectangle(cornerRadius: 6))
        }
    }
}

struct EpisodesList: View {
    let episodes: [CharacterDetail.Episode?]
    
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            ForEach(Array(episodes.enumerated()), id: \.element) { index, episode in
                if let episode = episode {
                    EpisodeRow(episode: episode, index: index)
                    
                    // Don't add divider after the last item
                    if index < episodes.count - 1 {
                        Divider()
                            .padding(.leading, 44)
                    }
                }
            }
        }
        .padding(.top, 12)
    }
}

struct EpisodeRow: View {
    let episode: CharacterDetail.Episode
    let index: Int
    
    var body: some View {
        HStack(spacing: 12) {
            // Episode number indicator
            ZStack {
                Circle()
                    .fill(Color.purple.opacity(0.15))
                    .frame(width: 32, height: 32)
                
                Text("\(index + 1)")
                    .font(.system(size: 14, weight: .bold))
                    .foregroundColor(.purple)
            }
            
            // Episode details
            VStack(alignment: .leading, spacing: 2) {
                Text(episode.name)
                    .font(.system(size: 16, weight: .medium))
                
                Text(episode.air_date)
                    .font(.system(size: 12))
                    .foregroundColor(.secondary)
            }
        }
        .padding(.vertical, 8)
    }
}
