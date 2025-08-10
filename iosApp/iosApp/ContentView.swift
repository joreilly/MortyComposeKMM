import SwiftUI
import shared


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

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
