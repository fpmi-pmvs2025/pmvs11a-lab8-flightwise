package by.bsu.flightwise.ui.activities

import android.content.Context
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import by.bsu.flightwise.R
import by.bsu.flightwise.ui.theme.FlightwiseTheme
import by.bsu.flightwise.ui.fragments.*
import java.io.FileOutputStream

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

    val context = LocalContext.current

    var expanded by remember { mutableStateOf(false) }

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

            Image(
                painter = painterResource(id = R.drawable.main_activity_header),
                contentDescription = "Main Activity Header",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.5f)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(id = R.string.countries_main_header),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 8.dp)
            )
            Text(
                text = stringResource(id = R.string.countries_main_description),
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
                Text(text = stringResource(id = R.string.countries_main_button_text), color = MaterialTheme.colorScheme.onPrimary)
            }

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = stringResource(id = R.string.flights_main_header),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 8.dp)
            )
            Text(
                text = stringResource(id = R.string.flights_main_description),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 1.dp)
            )
            Button(
                onClick = {
                    context.startActivity(Intent(context, SearchTicketsActivity::class.java))
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .width(160.dp),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(text = stringResource(id = R.string.flights_main_button_text), color = MaterialTheme.colorScheme.onPrimary)
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
