package by.bsu.flightwise

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Flightwise",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.weight(1f)
                )

                var expanded by remember { mutableStateOf(false) }

                Box {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                                .background(MaterialTheme.colorScheme.secondary)
                                .width(160.dp),
                    ) {
                        val ThemeColors = MenuDefaults.itemColors(
                            textColor = MaterialTheme.colorScheme.onSecondary,
                            leadingIconColor = MaterialTheme.colorScheme.secondary,
                            trailingIconColor = MaterialTheme.colorScheme.tertiary,
                            disabledTextColor = MaterialTheme.colorScheme.onTertiary,
                            disabledLeadingIconColor = MaterialTheme.colorScheme.onTertiary,
                            disabledTrailingIconColor = MaterialTheme.colorScheme.onTertiary
                        )

                        DropdownMenuItem(
                            onClick = { navigateToProfileActivity() },
                            text = { Text("Profile", style = MaterialTheme.typography.labelMedium) },
                            enabled = true,
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.secondary),
                            colors = ThemeColors,
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                        )
                        DropdownMenuItem(
                            onClick = { navigateToTicketsActivity() },
                            text = { Text("Tickets", style = MaterialTheme.typography.labelMedium) },
                            enabled = true,
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.secondary),
                            colors = ThemeColors,
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                        )
                        DropdownMenuItem(
                            onClick = { navigateToCountriesActivity() },
                            text = { Text("Contries", style = MaterialTheme.typography.labelMedium) },
                            enabled = true,
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.secondary),
                            colors = ThemeColors,

                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                        )
                        DropdownMenuItem(
                            onClick = { navigateToAboutActivity() },
                            text = { Text("About", style = MaterialTheme.typography.labelMedium) },
                            enabled = true,
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.secondary),
                            colors = ThemeColors,
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                }
            }
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
                .padding(top = 80.dp, bottom = 64.dp)
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
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.Start).padding(bottom = 8.dp)
            )
            Text(
                text = "To choose a ticket, you can firstly discover countries here",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.align(Alignment.Start).padding(start = 1.dp)
            )
            Button(
                onClick = { navigateToCountriesActivity() },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .width(160.dp),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(text = "Find out more", color = MaterialTheme.colorScheme.onPrimary)
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Section 2
            Text(
                text = "Ready to go",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.Start).padding(bottom = 8.dp)
            )
            Text(
                text = "If you know your choice, you can go ahead and buy a ticket!",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.align(Alignment.Start).padding(start = 1.dp)
            )
            Button(
                onClick = { navigateToSearchTicketsActivity() },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .width(160.dp),
                shape = RoundedCornerShape(4.dp)
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

private fun navigateToAboutActivity() {
    // val intent = Intent(this, AboutActivity::class.java)
    // startActivity(intent)
}

private fun navigateToProfileActivity() {
    // val intent = Intent(this, ProfileActivity::class.java)
    // startActivity(intent)
}

private fun navigateToTicketsActivity() {
    // val intent = Intent(this, TicketsActivity::class.java)
    // startActivity(intent)
}

private fun navigateToCountryActivity() {
    // val intent = Intent(this, CountryActivity::class.java)
    // startActivity(intent)
}
