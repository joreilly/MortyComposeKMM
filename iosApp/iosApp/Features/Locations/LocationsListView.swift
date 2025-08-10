import SwiftUI
import shared
import KMPObservableViewModelSwiftUI

struct LocationsListView: View {
    @StateViewModel var viewModel = LocationsViewModel()
    @State private var selectedLocation: LocationDetail? = nil
    
    var body: some View {
        List {
            ForEach(viewModel.locationsSnapshotList.indices, id: \.self) { index in
                if let location = viewModel.getElement(index: Int32(index)) {
                    NavigationLink(
                        destination: LocationDetailView(location: location),
                        tag: location,
                        selection: $selectedLocation
                    ) {
                        LocationsListRowView(location: location)
                            .onTapGesture {
                                selectedLocation = location
                            }
                    }
                }
            }
        }
        .navigationTitle("Locations")
    }
}

struct LocationDetailView: View {
    let location: LocationDetail
    
    var body: some View {
        VStack(alignment: .leading, spacing: 16) {
            Text(location.name)
                .font(.largeTitle)
                .fontWeight(.bold)
            
            Group {
                Text("Type: \(location.type)")
                Text("Dimension: \(location.dimension)")
                Text("Residents: \(location.residents.count)")
            }
            .font(.body)
            
            Spacer()
        }
        .padding()
        .navigationTitle(location.name)
    }
}