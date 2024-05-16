package dev.johnoreilly.mortycomposekmm.shared

import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class IosGreetingTest {

    @Test
    fun testExample() {
        // TODO this doesn't get passed getCharacters call right now....need to figure out why!
        runBlocking {
            val repository = MortyRepository()

//            val charactersResponse = repository.getCharacters(0)
//            println(charactersResponse?.results)
        }
    }
}