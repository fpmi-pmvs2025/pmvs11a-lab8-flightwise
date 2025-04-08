package by.bsu.flightwise

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import by.bsu.flightwise.ui.theme.FlightwiseTheme

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
    Box(modifier = Modifier.fillMaxSize()) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp)
                .align(Alignment.TopCenter)
        ) {
            Text(
                text = "Flightwise",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.headlineLarge
            )
        }

        // Footer
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "© 2025 Flightwise",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.labelSmall
            )
        }

        // Main Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 96.dp, bottom = 64.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Display Image
            Image(
                painter = painterResource(id = R.drawable.main_activity_header),
                contentDescription = "Main Activity Header",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.5f)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Section 1
            Text(
                text = "Discover countries",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "To choose a ticket, you can firstly discover countries here",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelSmall
            )
            Button(
                onClick = { navigateToCountriesActivity() },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "Find out more", color = MaterialTheme.colorScheme.onPrimary)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Section 2
            Text(
                text = "Ready to go",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "If you know your choice, you can go ahead and buy a ticket!",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelSmall
            )
            Button(
                onClick = { navigateToSearchTicketsActivity() },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "Buy a ticket", color = MaterialTheme.colorScheme.onPrimary)
            }
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
