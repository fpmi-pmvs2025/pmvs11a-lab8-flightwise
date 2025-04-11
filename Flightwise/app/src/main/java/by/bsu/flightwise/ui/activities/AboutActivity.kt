package by.bsu.flightwise.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import by.bsu.flightwise.ui.theme.FlightwiseTheme

class AboutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlightwiseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AboutScreen()
                }
            }
        }
    }
}

@Composable
fun AboutScreen() {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {

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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 80.dp, bottom = 64.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "This app was created by",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "Raitsyna Yuliya, Filippov Cyril, Senkevich Stanislau",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

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
    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    FlightwiseTheme {
        AboutScreen()
    }
}