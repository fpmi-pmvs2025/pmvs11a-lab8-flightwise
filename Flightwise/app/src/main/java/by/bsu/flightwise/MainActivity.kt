package by.bsu.flightwise

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import by.bsu.flightwise.ui.theme.FlightwiseTheme
import by.bsu.flightwise.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlightwiseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FlightwiseApp()
                }
            }
        }
    }
}

@Composable
fun FlightwiseApp() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Section 1
        Text(
            text = "Discover countries",
            style = MaterialTheme.typography.headlineLarge.copy(color = Highlight)
        )
        Text(
            text = "To choose a ticket, you can firstly discover countries here",
            style = MaterialTheme.typography.bodySmall.copy(color = Black)
        )
        Button(
            onClick = { navigateToCountriesActivity() },
            modifier = Modifier.padding(top = 16.dp),
            // colors = buttonColors(backgroundColor = Highlight)
        ) {
            Text(text = "Find out more", color = White)
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Section 2
        Text(
            text = "Ready to go",
            style = MaterialTheme.typography.headlineLarge.copy(color = Highlight)
        )
        Text(
            text = "If you know your choice, you can go ahead and buy a ticket!",
            style = MaterialTheme.typography.bodySmall.copy(color = Black)
        )
        Button(
            onClick = { navigateToSearchTicketsActivity() },
            modifier = Modifier.padding(top = 16.dp),
            // colors = ButtonDefaults.buttonColors(backgroundColor = Highlight)
        ) {
            Text(text = "Buy a ticket", color = White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FlightwiseAppPreview() {
    FlightwiseTheme {
        FlightwiseApp()
    }
}

private fun navigateToCountriesActivity() {
    // val intent = Intent(this, CountriesActivity::class.java)
    // startActivity(intent)
}

private fun navigateToSearchTicketsActivity() {
    // val intent = Intent(this, SearchTicketsActivity::class.java)
    // startActivity(intent)
}
