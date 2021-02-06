import Foundation
import SwiftUI
import shared

class CharacterListViewModel: ObservableObject {
    @Published public var characters: [CharacterDetail] = []
    let repository = MortyRepository()
    var nextPage: Int32? = 1
    
    func fetchCharacters() {
//        repository.getCharacters(page: nextPage!) { (data, error) in
//            if let newCharacters = data?.resultsFilterNotNull()?.map({ $0.fragments.characterDetail }) {
//                self.characters.append(contentsOf: newCharacters)
//            }
//            self.nextPage = data?.info?.next?.int32Value
//        }
        
        repository.characterPagingData.watch { nullablePagingData in
            guard let list = nullablePagingData?.compactMap({ $0 as? CharacterDetail }) else {
                return
            }
            
            self.characters = list
        }
    }
    
    
    func fetchNextData() {
        repository.characterPager.loadNext()
    }
    
    public var shouldDisplayNextPage: Bool {
        // TODO how do we check this using paging library?
        return nextPage != nil
    }
}

