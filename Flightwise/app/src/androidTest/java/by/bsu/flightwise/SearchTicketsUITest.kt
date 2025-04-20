package by.bsu.flightwise.ui.activities

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import by.bsu.flightwise.ui.theme.FlightwiseTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchTicketsUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenFieldsAreEmpty_showAllFieldsRequiredError() {
        composeTestRule.setContent {
            FlightwiseTheme {
                SearchTicketsForm()
            }
        }

        composeTestRule.onNodeWithText("Search").performClick()

        composeTestRule.onNodeWithText("All fields are required").assertIsDisplayed()
    }
}
