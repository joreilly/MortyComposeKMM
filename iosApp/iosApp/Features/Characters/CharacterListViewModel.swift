import Foundation
import SwiftUI
import shared

class CharacterListViewModel: ObservableObject {
    @Published public var characters: [CharacterDetail] = []
    let repository = MortyRepository()
    var nextPage: Int32? = 1
    
    func fetchCharacters() {
        repository.getCharacters(page: nextPage!) { (data, error) in
            if let newCharacters = data?.resultsFilterNotNull()?.map({ $0.fragments.characterDetail }) {
                self.characters.append(contentsOf: newCharacters)
            }
            self.nextPage = data?.info?.next?.int32Value
        }
        
    }
    
    public var shouldDisplayNextPage: Bool {
        return nextPage != nil
    }
}

