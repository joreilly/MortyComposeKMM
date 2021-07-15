import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import dev.johnoreilly.mortycomposekmm.fragment.CharacterDetail
import dev.johnoreilly.mortycomposekmm.ui.characters.CharactersListRowView
import org.junit.Rule
import org.junit.Test

class MortyConposeTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    val character = CharacterDetail("", "1", "John", "",
        "", "", "", emptyList(),
        CharacterDetail.Location("", ""), CharacterDetail.Origin(""))


    @Test
    fun testCharacterRow() {
        composeTestRule.setContent {
            CharactersListRowView(character = character, characterSelected = {})
        }
        composeTestRule.onRoot(useUnmergedTree = true).printToLog("characterRow")

        composeTestRule
            .onNode(
        hasText(character.name) and
                hasAnySibling(
                    hasText("0 episode(s)")
                ),
                useUnmergedTree = true
            )
            .assertExists()
    }
}