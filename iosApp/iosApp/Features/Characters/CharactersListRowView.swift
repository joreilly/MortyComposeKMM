import SwiftUI
import shared


struct CharactersListRowView: View {
    let character: CharacterDetail
    
    var body: some View {
        HStack {
            let url = URL(string: character.image) 
            AsyncImage(url: url) { image in
                image.resizable()
            } placeholder: {
                ProgressView()
            }
            .frame(width: 50, height: 50)
            .clipShape(RoundedRectangle(cornerRadius: 25))
            VStack(alignment: .leading) {
                Text(character.name )
                    .font(.title3)
                    .foregroundColor(.accentColor)
                Text("\(character.episode.count) episode(s)")
                    .font(.footnote)
                    .foregroundColor(.gray)
            }
        }
    }
}
