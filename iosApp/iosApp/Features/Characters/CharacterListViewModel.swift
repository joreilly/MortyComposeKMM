import Foundation
import SwiftUI
import shared

class CharacterListViewModel: ObservableObject {
    @Published public var characters: [GetCharactersQuery.Result] = []
    let repository = MortyRepository()
    var nextPage: Int32? = 1
    
    func fetchCharacters() {
        repository.getCharacters(page: nextPage!) { (data, error) in
            if let newCharacters = data?.results as? [GetCharactersQuery.Result] {
                self.characters.append(contentsOf: newCharacters)
            }
            self.nextPage = data?.info?.next?.int32Value
        }
        
    }
    
    public var shouldDisplayNextPage: Bool {
        return nextPage != nil
    }
}

