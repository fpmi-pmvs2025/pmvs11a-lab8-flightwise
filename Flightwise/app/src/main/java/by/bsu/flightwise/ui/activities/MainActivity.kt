package by.bsu.flightwise.ui.activities

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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import by.bsu.flightwise.R
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
    // Get the current context from Compose
    val context = LocalContext.current

    // Use a state value to manage the dropdown menu's expanded state
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {

        // Top App Bar with a dropdown menu
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
                            .width(160.dp)
                    ) {
                        val themeColors = MenuDefaults.itemColors(
                            textColor = MaterialTheme.colorScheme.onSecondary,
                            leadingIconColor = MaterialTheme.colorScheme.secondary,
                            trailingIconColor = MaterialTheme.colorScheme.tertiary,
                            disabledTextColor = MaterialTheme.colorScheme.onTertiary,
                            disabledLeadingIconColor = MaterialTheme.colorScheme.onTertiary,
                            disabledTrailingIconColor = MaterialTheme.colorScheme.onTertiary
                        )

                        DropdownMenuItem(
                            onClick = {
                                //context.startActivity(Intent(context, ProfileActivity::class.java))
                                expanded = false
                            },
                            text = { Text("Profile", style = MaterialTheme.typography.labelMedium) },
                            enabled = true,
                            modifier = Modifier.background(MaterialTheme.colorScheme.secondary),
                            colors = themeColors,
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                        )
                        DropdownMenuItem(
                            onClick = {
                                //context.startActivity(Intent(context, TicketsActivity::class.java))
                                expanded = false
                            },
                            text = { Text("Tickets", style = MaterialTheme.typography.labelMedium) },
                            enabled = true,
                            modifier = Modifier.background(MaterialTheme.colorScheme.secondary),
                            colors = themeColors,
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                        )
                        DropdownMenuItem(
                            onClick = {
                                //context.startActivity(Intent(context, CountriesActivity::class.java))
                                expanded = false
                            },
                            text = { Text("Countries", style = MaterialTheme.typography.labelMedium) },
                            enabled = true,
                            modifier = Modifier.background(MaterialTheme.colorScheme.secondary),
                            colors = themeColors,
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                        )
                        DropdownMenuItem(
                            onClick = {
                                context.startActivity(Intent(context, AboutActivity::class.java))
                                expanded = false
                            },
                            text = { Text("About", style = MaterialTheme.typography.labelMedium) },
                            enabled = true,
                            modifier = Modifier.background(MaterialTheme.colorScheme.secondary),
                            colors = themeColors,
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

            Image(
                painter = painterResource(id = R.drawable.main_activity_header),
                contentDescription = "Main Activity Header",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.5f)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Discover countries",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 8.dp)
            )
            Text(
                text = "To choose a ticket, you can firstly discover countries here",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 1.dp)
            )
            Button(
                onClick = {
                    // context.startActivity(Intent(context, CountriesActivity::class.java))
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .width(160.dp),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(text = "Find out more", color = MaterialTheme.colorScheme.onPrimary)
            }

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "Ready to go",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 8.dp)
            )
            Text(
                text = "If you know your choice, you can go ahead and buy a ticket!",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 1.dp)
            )
            Button(
                onClick = {
                    // context.startActivity(Intent(context, SearchTicketsActivity::class.java))
                },
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
