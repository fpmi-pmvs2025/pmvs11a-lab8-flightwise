package by.bsu.flightwise.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import by.bsu.flightwise.R
import by.bsu.flightwise.data.entity.Ticket
import by.bsu.flightwise.data.entity.Passenger
import by.bsu.flightwise.ui.fragments.FooterFragment
import by.bsu.flightwise.ui.fragments.HeaderFragment
import by.bsu.flightwise.ui.fragments.TicketFragment
import by.bsu.flightwise.ui.theme.FlightwiseTheme
import java.text.SimpleDateFormat
import java.util.*

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
    var showDialog by remember { mutableStateOf(false) }
    val passengers = remember { mutableStateListOf<Passenger>() }
    val context = LocalContext.current

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
                    text = stringResource(id = R.string.ticket_not_found),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Вывод списка пассажиров
            passengers.forEachIndexed { index, passenger ->
                PassengerFragment(passengerNumber = index + 1, passenger = passenger)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { showDialog = true },
                modifier = Modifier.width(160.dp),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.add_button_text),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val passengersList = ArrayList(passengers)
                    val intent = Intent(context, PaymentActivity::class.java).apply {
                        putParcelableArrayListExtra("passengers", passengersList)
                        putExtra("ticket", ticket)
                    }
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.continue_button_text),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        if (showDialog) {
            AddPassengerDialog(
                onDismiss = { showDialog = false },
                onAdd = { newPassenger ->
                    passengers.add(newPassenger)
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun PassengerFragment(passengerNumber: Int, passenger: Passenger) {
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
            Text(text = "Name: ${passenger.name}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Surname: ${passenger.surname}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Passport: ${passenger.passportNumber}", style = MaterialTheme.typography.bodyMedium)
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            Text(text = "Birth Date: ${dateFormat.format(passenger.birthDate)}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun AddPassengerDialog(
    onDismiss: () -> Unit,
    onAdd: (Passenger) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var passportNumber by remember { mutableStateOf("") }
    var birthDateText by remember { mutableStateOf("") }
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = stringResource(id = R.string.add_button_text))
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(text = stringResource(id = R.string.name_placeholder)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = surname,
                    onValueChange = { surname = it },
                    label = { Text(text = stringResource(id = R.string.surname_placeholder)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = passportNumber,
                    onValueChange = { passportNumber = it },
                    label = { Text(text = stringResource(id = R.string.passport_number_placeholder)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = birthDateText,
                    onValueChange = { birthDateText = it },
                    label = { Text(text = stringResource(id = R.string.birth_date_placeholder)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val parsedDate: Date? = try {
                        dateFormat.parse(birthDateText)
                    } catch (e: Exception) {
                        null
                    }
                    if (name.isNotBlank() && surname.isNotBlank() && passportNumber.isNotBlank() && parsedDate != null) {
                        onAdd(
                            Passenger(
                                name = name,
                                surname = surname,
                                passportNumber = passportNumber,
                                birthDate = parsedDate
                            )
                        )
                    }
                }
            ) {
                Text(text = stringResource(id = R.string.add_button_text))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(id = R.string.cancel_button_text))
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun TicketScreenPreview() {
    FlightwiseTheme {
        TicketScreen(ticket = null)
    }
}

