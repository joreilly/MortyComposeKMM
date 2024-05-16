import Foundation
import SwiftUI
import shared
import KMPNativeCoroutinesAsync

@MainActor
class CharacterListViewModel: ObservableObject {
    @Published public var characters: [CharacterDetail] = []
    @State var repository = MortyRepository()
    var hasNextPage: Bool = false
    
    func fetchCharacters() async {
         do {
            for try await characters in asyncSequence(for: repository.charactersSnapshotList) {
                self.characters = characters as! [CharacterDetail]
            }
        } catch {
            print("Failed with error: \(error)")
        }
    }
    
    
    func getElement(index: Int) -> CharacterDetail?  {
        return repository.charactersPagingDataPresenter.get(index: Int32(index))
    }
}

