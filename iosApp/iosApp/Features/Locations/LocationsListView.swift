import SwiftUI
import Shared

struct LocationsListView: View {
    @State private var viewModel = shared.viewmodel.LocationsViewModel()
    @State private var selectedLocation: LocationDetail? = nil
    
    var body: some View {
        Observing(viewModel.locationsLoadError) { error in
            if let error {
                LoadErrorView(message: error) { viewModel.retry() }
            } else {
                Observing(viewModel.locationsSnapshotList) { snapshotList in
                    List {
                        ForEach(snapshotList.indices, id: \.self) { index in
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