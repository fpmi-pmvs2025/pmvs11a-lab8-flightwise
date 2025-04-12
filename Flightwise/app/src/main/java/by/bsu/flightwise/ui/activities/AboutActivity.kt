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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import by.bsu.flightwise.R
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
                                //context.startActivity(Intent(context, ProfileActivity::class.java))
                                expanded = false
                            },
                            text = { Text(stringResource(id = R.string.menu_profile), style = MaterialTheme.typography.labelMedium) },
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
                            text = { Text(stringResource(id = R.string.menu_tickets), style = MaterialTheme.typography.labelMedium) },
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
                            text = { Text(stringResource(id = R.string.menu_countries), style = MaterialTheme.typography.labelMedium) },
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
                text = stringResource(id = R.string.about_creators_text),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = stringResource(id = R.string.team_members),
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
                text = stringResource(id = R.string.app_copyright),
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