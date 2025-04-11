package by.bsu.flightwise

import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import by.bsu.flightwise.ui.activities.AboutScreen
import by.bsu.flightwise.ui.theme.FlightwiseTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AboutActivityUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testHeaderDisplayedCorrectly() {
        composeTestRule.setContent {
            FlightwiseTheme {
                AboutScreen()
            }
        }

        composeTestRule.onNodeWithText("Flightwise").assertExists()
        composeTestRule.onNodeWithContentDescription("Menu").assertExists()
    }

    @Test
    fun testDropdownMenuItemsDisplayedCorrectly() {
        composeTestRule.setContent {
            FlightwiseTheme {
                AboutScreen()
            }
        }

        composeTestRule.onNodeWithContentDescription("Menu").performClick()

        composeTestRule.onNodeWithText("Profile").assertExists()
        composeTestRule.onNodeWithText("Tickets").assertExists()
        composeTestRule.onNodeWithText("Countries").assertExists()
        composeTestRule.onNodeWithText("About").assertExists()
    }

    @Test
    fun testMainContentDisplayedCorrectly() {
        composeTestRule.setContent {
            FlightwiseTheme {
                AboutScreen()
            }
        }

        composeTestRule.onNodeWithText("This app was created by").assertExists()
        composeTestRule.onNodeWithText("Raitsyna Yuliya, Filippov Cyril, Senkevich Stanislau").assertExists()
    }

    @Test
    fun testFooterDisplayedCorrectly() {
        composeTestRule.setContent {
            FlightwiseTheme {
                AboutScreen()
            }
        }

        composeTestRule.onNodeWithText("© 2025 Flightwise").assertExists()
    }
}
