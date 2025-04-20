package by.bsu.flightwise.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import by.bsu.flightwise.R
import by.bsu.flightwise.data.dao.TicketDao
import by.bsu.flightwise.data.dao.impl.TicketDaoImpl
import by.bsu.flightwise.data.database.DatabaseHelper
import by.bsu.flightwise.data.entity.Ticket
import by.bsu.flightwise.ui.fragments.FooterFragment
import by.bsu.flightwise.ui.fragments.HeaderFragment
import by.bsu.flightwise.ui.fragments.TicketFragment
import by.bsu.flightwise.ui.theme.FlightwiseTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Если пользователь не авторизован, перенаправляем в LoginActivity
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
                    ProfileScreen()
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
fun ProfileScreen() {
    val context = androidx.compose.ui.platform.LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Извлекаем имя пользователя и идентификатор пассажира из SharedPreferences
    val preferences = context.getSharedPreferences("Session", Context.MODE_PRIVATE)
    val userName = preferences.getString("sessionID", "User")
        ?: "User"
    val passengerId = preferences.getLong("passengerId", -1L)

    var tickets by remember { mutableStateOf<List<Ticket>>(emptyList()) }

    // Загружаем билеты для данного пассажира
    LaunchedEffect(userName) {
        val dbHelper = DatabaseHelper(context)
        val db = dbHelper.readableDatabase
        val ticketDao: TicketDao = TicketDaoImpl(db)
        tickets = withContext(Dispatchers.IO) {
            ticketDao.searchByPassengerName(userName)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        HeaderFragment(modifier = Modifier.align(Alignment.TopCenter))
        FooterFragment(modifier = Modifier.align(Alignment.BottomCenter))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 120.dp, bottom = 64.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // Вывод данных пользователя (слева)
            Text(
                text = userName,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = stringResource(id = R.string.profile_passenger_role),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Заголовок "Your tickets" – синий
            Text(
                text = stringResource(id = R.string.profile_tickets_text),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Отображение билетов или предупреждение, если билетов нет
            if (tickets.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.message_nothing_found),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            } else {
                tickets.forEach { ticket ->
                    TicketFragment(ticket = ticket)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    FlightwiseTheme {
        ProfileScreen()
    }
}
