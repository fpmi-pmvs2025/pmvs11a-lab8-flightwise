package by.bsu.flightwise.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import by.bsu.flightwise.data.entity.Ticket
import by.bsu.flightwise.data.entity.TicketStatus
import by.bsu.flightwise.ui.fragments.FooterFragment
import by.bsu.flightwise.ui.fragments.HeaderFragment
import by.bsu.flightwise.ui.theme.FlightwiseTheme
import by.bsu.flightwise.ui.fragments.TicketFragment
import java.util.Date

class TicketsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlightwiseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TicketsScreen()
                }
            }
        }
    }
}

@Composable
fun TicketsScreen() {

    var withLuggage by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {

        HeaderFragment(
            modifier = Modifier
                .align(Alignment.TopCenter)
        )

        FooterFragment(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 80.dp, bottom = 64.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    Checkbox(
                        checked = withLuggage,
                        onCheckedChange = { withLuggage = it }
                    )
                    Text(text = "With luggage")
                }

                TicketList(withLuggage = withLuggage)
            }
        }
    }
}

@Composable
fun TicketList(withLuggage: Boolean) {

    val tickets = if (withLuggage) {
        listOf(
            Ticket(
                id = 1L,
                passengerId = 100L,
                paymentId = null,
                flightId = 101L,
                price = 100.0f,
                hasLuggage = true,
                bookedAt = Date(),
                status = TicketStatus.PENDING
            ),
            Ticket(
                id = 2L,
                passengerId = 100L,
                paymentId = null,
                flightId = 102L,
                price = 150.0f,
                hasLuggage = true,
                bookedAt = Date(),
                status = TicketStatus.BOOKED
            ),
            Ticket(
                id = 3L,
                passengerId = 100L,
                paymentId = null,
                flightId = 103L,
                price = 200.0f,
                hasLuggage = true,
                bookedAt = Date(),
                status = TicketStatus.CANCELED
            )
        )
    } else {
        listOf(
            Ticket(
                id = 4L,
                passengerId = 100L,
                paymentId = null,
                flightId = 104L,
                price = 100.0f,
                hasLuggage = false,
                bookedAt = Date(),
                status = TicketStatus.PENDING
            ),
            Ticket(
                id = 5L,
                passengerId = 100L,
                paymentId = null,
                flightId = 105L,
                price = 120.0f,
                hasLuggage = false,
                bookedAt = Date(),
                status = TicketStatus.PENDING
            ),
            Ticket(
                id = 6L,
                passengerId = 100L,
                paymentId = null,
                flightId = 106L,
                price = 140.0f,
                hasLuggage = false,
                bookedAt = Date(),
                status = TicketStatus.PENDING
            )
        )
    }

    LazyColumn(contentPadding = PaddingValues(vertical = 8.dp)) {
        items(tickets) { ticket ->
            TicketFragment(ticket = ticket)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TicketsScreenPreview() {
    FlightwiseTheme {
        TicketsScreen()
    }
}
