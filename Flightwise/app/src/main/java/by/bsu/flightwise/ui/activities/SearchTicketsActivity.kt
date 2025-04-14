package by.bsu.flightwise.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import by.bsu.flightwise.R
import by.bsu.flightwise.ui.theme.FlightwiseTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchTicketsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            FlightwiseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SearchTicketsForm()
                }
            }
        }
    }
}

@Composable
fun SearchTicketsForm() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var from by remember { mutableStateOf("") }
    var to by remember { mutableStateOf("") }
    var dateOfLeaving by remember { mutableStateOf("") }
    var dateOfReturn by remember { mutableStateOf("") }
    var numberOfPassengers by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 80.dp, bottom = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {



        OutlinedTextField(
            value = from,
            onValueChange = { from = it },
            label = { Text("From") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = to,
            onValueChange = { to = it },
            label = { Text("To") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = dateOfLeaving,
                onValueChange = { dateOfLeaving = it },
                label = { Text("Date of leaving") },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(
                value = dateOfReturn,
                onValueChange = { dateOfReturn = it },
                label = { Text("Date of return") },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = numberOfPassengers,
            onValueChange = { numberOfPassengers = it },
            label = { Text("Number of passengers") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (from.isEmpty() || to.isEmpty() || dateOfLeaving.isEmpty() ||
                    dateOfReturn.isEmpty() || numberOfPassengers.isEmpty()
                ) {
                    errorMessage = "All fields are required"
                } else {
                    errorMessage = ""
                    isLoading = true

                    coroutineScope.launch {
                        delay(1000)
                        isLoading = false

                        //context.startActivity(Intent(context, TicketsActivity::class.java))
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading,
            shape = MaterialTheme.shapes.medium
        ) {
            // Crossfade to animate the button content change.
            Crossfade(targetState = isLoading) { loading ->
                if (loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(text = "Search")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchTicketsFormPreview() {
    FlightwiseTheme {
        SearchTicketsForm()
    }
}
