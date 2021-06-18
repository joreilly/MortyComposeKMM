import Foundation
import SwiftUI
import shared

class CharacterListViewModel: ObservableObject {
    @Published public var characters: [CharacterDetail] = []
    let repository = MortyRepository()
    var hasNextPage: Bool = false
    
    func fetchCharacters() {
        repository.characterPagingData.watch { nullablePagingData in
            guard let list = nullablePagingData?.compactMap({ $0 as? CharacterDetail }) else {
                return
            }
            
            self.characters = list
            self.hasNextPage = self.repository.characterPager.hasNextPage
        }
    }
    
    
    func fetchNextData() {
        repository.characterPager.loadNext()
    }
    
    public var shouldDisplayNextPage: Bool {
        return hasNextPage
    }
}

