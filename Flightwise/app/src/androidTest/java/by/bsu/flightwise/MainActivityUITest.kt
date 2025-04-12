package by.bsu.flightwise

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import by.bsu.flightwise.ui.activities.FlightwiseApp
import by.bsu.flightwise.ui.theme.FlightwiseTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testHeaderDisplayedCorrectly() {
        composeTestRule.setContent {
            FlightwiseTheme {
                FlightwiseApp()
            }
        }

        composeTestRule.onNodeWithText("Flightwise").assertExists()
        composeTestRule.onNodeWithContentDescription("Menu").assertExists()
    }

    @Test
    fun testMenuItemsDisplayedCorrectly() {
        composeTestRule.setContent {
            FlightwiseTheme {
                FlightwiseApp()
            }
        }

        composeTestRule.onNodeWithContentDescription("Menu").performClick()

        composeTestRule.onNodeWithText("Profile").assertExists()
        composeTestRule.onNodeWithText("Tickets").assertExists()
        composeTestRule.onNodeWithText("Countries").assertExists()
        composeTestRule.onNodeWithText("About").assertExists()
    }

    @Test
    fun testFooterDisplayedCorrectly() {
        composeTestRule.setContent {
            FlightwiseTheme {
                FlightwiseApp()
            }
        }

        composeTestRule.onNodeWithText("© 2025 Flightwise").assertExists()
    }

    @Test
    fun testDiscoverCountriesSection() {
        composeTestRule.setContent {
            FlightwiseTheme {
                FlightwiseApp()
            }
        }

        composeTestRule.onNodeWithText("Discover countries").assertExists()
        composeTestRule.onNodeWithText("To choose a ticket, you can firstly discover countries here!").assertExists()
        composeTestRule.onNodeWithText("Find out more").assertExists()
    }

    @Test
    fun testReadyToGoSection() {
        composeTestRule.setContent {
            FlightwiseTheme {
                FlightwiseApp()
            }
        }

        composeTestRule.onNodeWithText("Ready to go").assertExists()
        composeTestRule.onNodeWithText("If you know your choice, you can go ahead and buy a ticket!").assertExists()
        composeTestRule.onNodeWithText("Buy a ticket").assertExists()
    }

    @Test
    fun testButtonClicks() {
        composeTestRule.setContent {
            FlightwiseTheme {
                FlightwiseApp()
            }
        }

        composeTestRule.onNodeWithText("Find out more").performClick()

        composeTestRule.onNodeWithText("Buy a ticket").performClick()
    }
}
