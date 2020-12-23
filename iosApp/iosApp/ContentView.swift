import SwiftUI
import shared


struct ContentView: View {
    var body: some View {
        TabView {
            CharactersListView()
                .tabItem {
                    Label("Characters", systemImage: "person.crop.square.fill.and.at.rectangle")
                }
            EpisodesListView()
                .tabItem {
                    Label("Episodes", systemImage: "tv")
                }
        }
    }
    
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
