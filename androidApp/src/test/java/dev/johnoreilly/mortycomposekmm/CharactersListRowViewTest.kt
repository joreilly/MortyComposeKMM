package dev.johnoreilly.mortycomposekmm

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import dev.johnoreilly.mortycomposekmm.fragment.CharacterDetail
import dev.johnoreilly.mortycomposekmm.ui.characters.CharactersListRowView
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

// Compose UI test run on the JVM with Robolectric (no emulator); the Maestro flow covers the
// real app end to end against the live API.
// A plain Application: the app's own starts Koin, which this composable doesn't need (and which
// can't be started twice across tests).
@RunWith(RobolectricTestRunner::class)
// SDK 35: Robolectric's SDK 36 runtime needs Java 21, and CI runs the tests on 17
@Config(application = android.app.Application::class, sdk = [35])
class CharactersListRowViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val character = CharacterDetail(
        id = "1",
        name = "Rick Sanchez",
        image = "",
        status = "Alive",
        species = "Human",
        type = "",
        gender = "Male",
        episode = listOf(
            CharacterDetail.Episode(id = "1", name = "Pilot", air_date = "December 2, 2013"),
            CharacterDetail.Episode(id = "2", name = "Lawnmower Dog", air_date = "December 9, 2013"),
        ),
        location = CharacterDetail.Location(id = "3", name = "Citadel of Ricks"),
        origin = CharacterDetail.Origin(name = "Earth (C-137)"),
    )

    @Test
    fun showsCharacterDetails() {
        composeTestRule.setContent {
            CharactersListRowView(character = character, characterSelected = {})
        }

        composeTestRule.onNodeWithText("Rick Sanchez").assertIsDisplayed()
        composeTestRule.onNodeWithText("Alive - Human", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Last location: Citadel of Ricks").assertIsDisplayed()
        composeTestRule.onNodeWithText("Episodes:").assertIsDisplayed()
        composeTestRule.onNodeWithText("2").assertIsDisplayed()
    }

    @Test
    fun clickSelectsCharacter() {
        var selected: CharacterDetail? = null
        composeTestRule.setContent {
            CharactersListRowView(character = character, characterSelected = { selected = it })
        }

        composeTestRule.onNodeWithText("Rick Sanchez").performClick()

        assertEquals(character, selected)
    }
}
