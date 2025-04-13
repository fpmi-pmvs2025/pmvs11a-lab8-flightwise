package by.bsu.flightwise

import android.content.Intent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import by.bsu.flightwise.ui.activities.RegisterScreen
import by.bsu.flightwise.ui.theme.FlightwiseTheme
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterActivityUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testRegisterScreenComponentsDisplayed() {
        composeTestRule.setContent {
            FlightwiseTheme {
                RegisterScreen()
            }
        }
        composeTestRule.onAllNodesWithText("Register").assertCountEquals(2)
        composeTestRule.onNodeWithText("Username").assertExists()
        composeTestRule.onNodeWithText("Password").assertExists()
        composeTestRule.onNodeWithText("Confirm password").assertExists()
        composeTestRule.onNodeWithText("Already have an account?").assertExists()
    }

    @Test
    fun testEmptyFieldsShowsErrorMessage() {
        composeTestRule.setContent {
            FlightwiseTheme {
                RegisterScreen()
            }
        }
        composeTestRule.onNode(hasText("Register") and hasClickAction()).performClick()
        composeTestRule.onNodeWithText("Please fill in all fields.").assertExists()
    }

    @Test
    fun testPasswordMismatchShowsErrorMessage() {
        composeTestRule.setContent {
            FlightwiseTheme {
                RegisterScreen()
            }
        }
        composeTestRule.onNodeWithText("Username").performTextInput("testUser")
        composeTestRule.onNodeWithText("Password").performTextInput("password123")
        composeTestRule.onNodeWithText("Confirm password").performTextInput("password321")
        composeTestRule.onNode(hasText("Register") and hasClickAction()).performClick()
        composeTestRule.onNodeWithText("Passwords do not match.").assertExists()
    }


}
