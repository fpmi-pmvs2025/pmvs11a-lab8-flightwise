package by.bsu.flightwise

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import by.bsu.flightwise.data.entity.Passenger
import by.bsu.flightwise.data.entity.Ticket
import by.bsu.flightwise.data.entity.TicketStatus
import by.bsu.flightwise.ui.activities.AddPassengerDialog
import by.bsu.flightwise.ui.activities.TicketScreen
import by.bsu.flightwise.ui.theme.FlightwiseTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date

@RunWith(AndroidJUnit4::class)
class TicketActivityUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val dummyTicket = Ticket(
        id = 1L,
        passengerId = 100L,
        paymentId = null,
        flightId = 1L,
        price = 123.45f,
        hasLuggage = false,
        bookedAt = Date(),
        status = TicketStatus.PENDING
    )

    @Test
    fun ticketScreen_whenTicketIsNull_displaysTicketNotFoundText() {
        composeTestRule.setContent {
            FlightwiseTheme {
                TicketScreen(ticket = null)
            }
        }
        composeTestRule.onNodeWithText("Ticket not found.").assertIsDisplayed()
    }

    @Test
    fun ticketScreen_whenTicketProvided_displaysContinueButton() {
        composeTestRule.setContent {
            FlightwiseTheme {
                TicketScreen(ticket = dummyTicket)
            }
        }
        composeTestRule.onNodeWithText("Continue").assertIsDisplayed()
    }

    @Test
    fun ticketScreen_clickAddPassenger_showsAddPassengerDialog() {
        composeTestRule.setContent {
            FlightwiseTheme {
                TicketScreen(ticket = dummyTicket)
            }
        }
        composeTestRule.onNodeWithText("Add").performClick()

        composeTestRule.onNodeWithText("Name").assertIsDisplayed()
    }

}
