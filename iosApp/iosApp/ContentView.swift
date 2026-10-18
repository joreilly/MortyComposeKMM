import SwiftUI
import Shared

// Apollo fragments live in their own Kotlin package; alias them so views can name them directly
typealias CharacterDetail = fragment.CharacterDetail
typealias EpisodeDetail = fragment.EpisodeDetail
typealias LocationDetail = fragment.LocationDetail


struct ContentView: View {
    var body: some View {
        TabView {
            NavigationView {
                CharactersListView()
            }
            .tabItem {
                Label("Characters", systemImage: "person.crop.square.fill.and.at.rectangle")
            }
            
            NavigationView {
                EpisodesListView()
            }
            .tabItem {
                Label("Episodes", systemImage: "tv")
            }
            
            NavigationView {
                LocationsListView()
            }
            .tabItem {
                Label("Locations", systemImage: "location")
            }
        }
    }
}

/// Shown in place of a list whose first page failed to load, which would otherwise just be empty.
struct LoadErrorView: View {
    let message: String
    let retry: () -> Void

    var body: some View {
        VStack(spacing: 16) {
            Image(systemName: "exclamationmark.triangle")
                .font(.largeTitle)
                .foregroundColor(.secondary)
            Text(message)
                .font(.headline)
            Button("Retry", action: retry)
                .buttonStyle(.borderedProminent)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
