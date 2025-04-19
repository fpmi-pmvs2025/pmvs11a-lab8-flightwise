package by.bsu.flightwise.ui.activities

import DatePickerField
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import by.bsu.flightwise.R
import by.bsu.flightwise.data.dao.impl.FlightDaoImpl
import by.bsu.flightwise.data.database.DatabaseHelper
import by.bsu.flightwise.ui.fragments.FooterFragment
import by.bsu.flightwise.ui.fragments.HeaderFragment
import by.bsu.flightwise.ui.theme.FlightwiseTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SearchTicketsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!isUserAuthorized()) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

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

    private fun isUserAuthorized(): Boolean {
        val preferences = getSharedPreferences("Session", Context.MODE_PRIVATE)
        val sessionID = preferences.getString("sessionID", null)
        return !sessionID.isNullOrEmpty()
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
                .padding(top = 160.dp, bottom = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = from,
                onValueChange = { from = it },
                label = { Text(text = stringResource(id = R.string.flights_from_placeholder)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = to,
                onValueChange = { to = it },
                label = { Text(text = stringResource(id = R.string.flights_to_placeholder)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                DatePickerField(
                    label = stringResource(id = R.string.flights_leave_date_placeholder),
                    selectedDate = dateOfLeaving,
                    onDateSelected = { dateOfLeaving = it },
                    modifier = Modifier.weight(1f)
                )
                DatePickerField(
                    label = stringResource(id = R.string.flights_return_date_placeholder),
                    selectedDate = dateOfReturn,
                    onDateSelected = { dateOfReturn = it },
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = numberOfPassengers,
                onValueChange = { numberOfPassengers = it },
                label = { Text(text = stringResource(id = R.string.flights_passengers_placeholder)) },
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
                        errorMessage = stringResource(id = R.string.error_all_fields_required)
                    } else {
                        errorMessage = ""
                        isLoading = true

                        coroutineScope.launch {
                            try {
                                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                                val startDate: Date? = sdf.parse(dateOfLeaving)
                                val endDate: Date? = sdf.parse(dateOfReturn)

                                println("Dates: $startDate $endDate")

                                if (startDate == null || endDate == null) {
                                    errorMessage = stringResource(id = R.string.error_incorrect_date_format)
                                    isLoading = false
                                    return@launch
                                }

                                val dbHelper = DatabaseHelper(context)
                                val db = dbHelper.readableDatabase
                                val flightDao = FlightDaoImpl(db)

                                val flightsCities = withContext(Dispatchers.IO) {
                                    flightDao.findByCitiesAndDateRange(from, to, startDate, endDate)
                                }
                                val flightsCountries = withContext(Dispatchers.IO) {
                                    flightDao.findByCountriesAndDateRange(from, to, startDate, endDate)
                                }

                                val intent = Intent(context, TicketsActivity::class.java).apply {
                                    putExtra("flights", ArrayList(flightsCities + flightsCountries))
                                }
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                println("Error: ${e.message}")
                            } finally {
                                isLoading = false
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading,
                shape = RoundedCornerShape(4.dp)
            ) {
                Crossfade(targetState = isLoading) { loading ->
                    if (loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.primary,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(text = stringResource(id = R.string.search_button_text))
                    }
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
