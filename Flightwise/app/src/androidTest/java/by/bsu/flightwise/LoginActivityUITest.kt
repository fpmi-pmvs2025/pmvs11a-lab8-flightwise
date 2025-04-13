package by.bsu.flightwise

import android.content.Intent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import by.bsu.flightwise.ui.activities.LoginScreen
import by.bsu.flightwise.ui.theme.FlightwiseTheme
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LoginActivityUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLoginScreenComponentsDisplayed() {
        composeTestRule.setContent {
            FlightwiseTheme {
                LoginScreen()
            }
        }

        composeTestRule.onAllNodesWithText("Login").assertCountEquals(2)

        composeTestRule.onNodeWithText("Username").assertExists()

        composeTestRule.onNodeWithText("Password").assertExists()
    }

    @Test
    fun testEmptyFieldsShowsErrorMessage() {
        composeTestRule.setContent {
            FlightwiseTheme {
                LoginScreen()
            }
        }
        composeTestRule.onNode(hasText("Login") and hasClickAction()).performClick()
        composeTestRule.onNodeWithText("Please fill in all fields.").assertExists()
    }

    @Test
    fun testInvalidLoginShowsErrorMessage() {
        composeTestRule.setContent {
            FlightwiseTheme {
                LoginScreen()
            }
        }
        composeTestRule.onNodeWithText("Username").performTextInput("testUser")
        composeTestRule.onNodeWithText("Password").performTextInput("wrongPassword")
        composeTestRule.onNode(hasText("Login") and hasClickAction()).performClick()
        composeTestRule.onNodeWithText("Please, check filled data").assertExists()
    }

}
