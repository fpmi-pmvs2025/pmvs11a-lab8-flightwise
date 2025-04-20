package by.bsu.flightwise

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import by.bsu.flightwise.data.entity.Flight
import by.bsu.flightwise.ui.activities.TicketsScreen
import by.bsu.flightwise.ui.theme.FlightwiseTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date

@RunWith(AndroidJUnit4::class)
class TicketsActivityUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun ticketsScreen_checkboxTogglesState() {
        composeTestRule.setContent {
            FlightwiseTheme {
                TicketsScreen(flights = emptyList())
            }
        }
        val toggleableNode = composeTestRule.onNode(isToggleable())

        toggleableNode.assertIsOff()

        toggleableNode.performClick()

        toggleableNode.assertIsOn()
    }
}
