package by.bsu.flightwise.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import by.bsu.flightwise.R
import by.bsu.flightwise.data.entity.Ticket
import by.bsu.flightwise.ui.fragments.FooterFragment
import by.bsu.flightwise.ui.fragments.HeaderFragment
import by.bsu.flightwise.ui.fragments.TicketFragment
import by.bsu.flightwise.ui.theme.FlightwiseTheme

class TicketActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ticket = intent.getParcelableExtra<Ticket>("ticket")

        setContent {
            FlightwiseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TicketScreen(ticket = ticket)
                }
            }
        }
    }
}

@Composable
fun TicketScreen(ticket: Ticket?) {
    var passengerCount by remember { mutableStateOf(1) }

    Box(modifier = Modifier.fillMaxSize()) {

        HeaderFragment(
            modifier = Modifier.align(Alignment.TopCenter)
        )

        FooterFragment(
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 100.dp, bottom = 64.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (ticket != null) {
                TicketFragment(ticket = ticket)
            } else {
                Text(
                    text = "Ticket not found.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                repeat(passengerCount) { index ->
                    PassengerFragment(passengerNumber = index + 1)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { passengerCount++ },
                    modifier = Modifier.width(160.dp),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = "Add Passenger",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { println("continue pressed") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(text = "Continue", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Composable
fun PassengerFragment(passengerNumber: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Passenger $passengerNumber",
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TicketScreenPreview() {
    FlightwiseTheme {
        TicketScreen(ticket = null)
    }
}
