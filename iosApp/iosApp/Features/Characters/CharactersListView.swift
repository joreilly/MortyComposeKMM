import SwiftUI

struct CharactersListView: View {
    @StateObject private var data = CharacterListViewModel()
    
    var body: some View {
        List {
            ForEach(data.characters, id: \.id) { character in
                CharactersListRowView(character: character)
            }
            if data.shouldDisplayNextPage {
                nextPageView
            }
        }
        .navigationTitle("Characters")
        .onAppear {
            data.fetchCharacters()
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
