import SwiftUI
import shared

struct LocationsListRowView: View {
    let location: LocationDetail
    
    var body: some View {
        VStack(alignment: .leading) {
            Text(location.name)
                .font(.title3)
                .foregroundColor(.accentColor)
                .fontWeight(.bold)
                .lineLimit(1)
            
            Text("\(location.residents.count) resident(s)")
                .font(.footnote)
                .foregroundColor(.gray)
        }
        .padding(.vertical, 4)
    }
}