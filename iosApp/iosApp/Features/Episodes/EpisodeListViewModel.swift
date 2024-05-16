import Foundation
import SwiftUI
import shared
import KMPNativeCoroutinesAsync

class EpisodeListViewModel: ObservableObject {
    @Published public var episodes: [EpisodeDetail] = []
    @State var repository = MortyRepository()
    var hasNextPage: Bool = false
    
    func fetchEpisodes() async {
        do {
           for try await episodes in asyncSequence(for: repository.episodesSnapshotList) {
               self.episodes = episodes as! [EpisodeDetail]
           }
       } catch {
           print("Failed with error: \(error)")
       }
    }
    
    func getElement(index: Int) -> EpisodeDetail?  {
        return repository.episodesPagingDataPresenter.get(index: Int32(index))
    }

}

