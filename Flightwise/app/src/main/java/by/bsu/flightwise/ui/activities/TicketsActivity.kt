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
import by.bsu.flightwise.data.entity.Flight
import by.bsu.flightwise.data.entity.PricingRule
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

        val flights = intent.getParcelableArrayListExtra<Flight>("flights") ?: arrayListOf()

        setContent {
            FlightwiseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TicketsScreen(flights = flights)
                }
            }
        }
    }
}

@Composable
fun TicketsScreen(flights: List<Flight>) {

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

                TicketList(withLuggage = withLuggage, flights = flights)
            }
        }
    }
}

@Composable
fun TicketList(withLuggage: Boolean, flights: List<Flight>) {

    fun getLuggageFeePercentage(pricingRuleId: Long): Float {
        return when (pricingRuleId) {
            1L -> 0.1f
            2L -> 0.15f
            3L -> 0.05f
            4L -> 0.2f
            5L -> 0.25f
            6L -> 0.08f
            7L -> 0.12f
            8L -> 0.18f
            9L -> 0.2f
            10L -> 0.3f
            11L -> 0.1f
            12L -> 0.25f
            else -> 0.1f
        }
    }
    val basePrice = 100.0f

    val tickets = flights.map { flight ->
        val feePercentage = getLuggageFeePercentage(flight.pricingRuleId)
        val luggageFee = if (withLuggage) basePrice * feePercentage else 0.0f
        val finalPrice = (basePrice + luggageFee) * ( 1.0f + feePercentage )

        Ticket(
            id = flight.id,
            passengerId = 100L,
            paymentId = null,
            flightId = flight.id,
            price = finalPrice,
            hasLuggage = withLuggage,
            bookedAt = flight.departureTime,
            status = if (withLuggage) TicketStatus.BOOKED else TicketStatus.PENDING
        )
    }

    LazyColumn(contentPadding = PaddingValues(vertical = 8.dp)) {
        items(tickets) { ticket ->
            TicketFragment(ticket = ticket)
        }
    }
}


