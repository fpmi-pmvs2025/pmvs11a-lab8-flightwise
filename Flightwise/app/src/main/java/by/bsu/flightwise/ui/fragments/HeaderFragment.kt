package by.bsu.flightwise.ui.fragments

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.*
import androidx.compose.runtime.*

import by.bsu.flightwise.R
import by.bsu.flightwise.ui.activities.AboutActivity
import by.bsu.flightwise.ui.activities.SearchTicketsActivity

@Composable
fun HeaderFragment(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                stringResource(id = R.string.app_name),
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
                            context.startActivity(Intent(context, SearchTicketsActivity::class.java))
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
                    DropdownMenuItem(
                        onClick = {
                            context.startActivity(Intent(context, AboutActivity::class.java))
                            expanded = false
                        },
                        text = { Text(stringResource(id = R.string.menu_about), style = MaterialTheme.typography.labelMedium) },
                        enabled = true,
                        modifier = Modifier.background(MaterialTheme.colorScheme.secondary),
                        colors = themeColors,
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}