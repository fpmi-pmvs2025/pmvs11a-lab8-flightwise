package by.bsu.flightwise.ui.fragments

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import by.bsu.flightwise.R
import by.bsu.flightwise.data.dao.impl.AirplaneDaoImpl
import by.bsu.flightwise.data.dao.impl.AirportDaoImpl
import by.bsu.flightwise.data.dao.impl.FlightDaoImpl
import by.bsu.flightwise.data.database.DatabaseHelper
import by.bsu.flightwise.data.entity.Airplane
import by.bsu.flightwise.data.entity.Airport
import by.bsu.flightwise.data.entity.Flight
import by.bsu.flightwise.data.entity.Ticket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun TicketFragment(ticket: Ticket) {
    val context = LocalContext.current

    var flight by remember { mutableStateOf<Flight?>(null) }
    var airplane by remember { mutableStateOf<Airplane?>(null) }
    var departureAirport by remember { mutableStateOf<Airport?>(null) }
    var arrivalAirport by remember { mutableStateOf<Airport?>(null) }

    LaunchedEffect(ticket.flightId) {
        withContext(Dispatchers.IO) {
            try {
                val dbHelper = DatabaseHelper(context)
                val db = dbHelper.readableDatabase

                val flightDao = FlightDaoImpl(db)
                flight = flightDao.getById(ticket.flightId)

                flight?.let { f ->
                    val airplaneDao = AirplaneDaoImpl(db)
                    airplane = airplaneDao.getById(f.airplaneId)
                    val airportDao = AirportDaoImpl(db)
                    departureAirport = airportDao.getById(f.fromAirportId)
                    arrivalAirport = airportDao.getById(f.toAirportId)
                }
                db.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    if (flight == null || airplane == null || departureAirport == null || arrivalAirport == null) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.width(IntrinsicSize.Min)) {
                    Text(
                        text = departureAirport!!.code,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = timeFormatter.format(flight!!.departureTime),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Divider(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.airplane),
                        contentDescription = "Flight",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Column(
                    modifier = Modifier.width(IntrinsicSize.Min),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = arrivalAirport!!.code,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = timeFormatter.format(flight!!.arrivalTime),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Flight No: ${flight!!.id}",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Airplane: ${airplane!!.model}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "$${"%.2f".format(ticket.price)}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Price for ticket",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                if (ticket.hasLuggage) {
                    Icon(
                        painter = painterResource(id = R.drawable.luggage),
                        contentDescription = "Luggage Included",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    }
}
