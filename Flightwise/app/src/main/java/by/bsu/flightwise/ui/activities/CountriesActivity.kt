package by.bsu.flightwise.ui.activities

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import by.bsu.flightwise.R
import by.bsu.flightwise.ui.fragments.CountryFragment
import by.bsu.flightwise.ui.fragments.FooterFragment
import by.bsu.flightwise.ui.fragments.HeaderFragment
import by.bsu.flightwise.ui.theme.FlightwiseTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class CountriesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlightwiseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CountriesApp()
                }
            }
        }
    }
}

@Composable
fun CountriesApp() {
    var countries by remember { mutableStateOf<List<Country>>(emptyList()) }
    var searchQuery by remember { mutableStateOf("") }
    var notFound by remember { mutableStateOf(false) }

    LaunchedEffect(searchQuery) {
        val url = if (searchQuery.trim().isEmpty())
            "https://restcountries.com/v3.1/all"
        else
            "https://restcountries.com/v3.1/name/${searchQuery.trim()}"

        val responseString = fetchResponse(url)

        if (responseString != null) {
            try {
                val jsonArray = JSONArray(responseString)
                if (jsonArray.length() > 0) {
                    countries = (0 until jsonArray.length()).map { i ->
                        val jsonObject: JSONObject = jsonArray.getJSONObject(i)
                        val name = jsonObject.getJSONObject("name").getString("common")
                        var capital = "N/A"
                        if (jsonObject.has("capital")) {
                            val capitals = jsonObject.getJSONArray("capital")
                            if (capitals.length() > 0) {
                                capital = capitals.getString(0)
                            }
                        }
                        val flagUrl = jsonObject.getJSONObject("flags").getString("png")
                        Country(name = name, capital = capital, flagUrl = flagUrl)
                    }
                    notFound = false
                } else {
                    countries = emptyList()
                    notFound = true
                }
            } catch (e: Exception) {
                countries = emptyList()
                notFound = true
            }
        } else {
            countries = emptyList()
            notFound = true
        }
    }

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
                .padding(top = 100.dp, bottom = 64.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text(stringResource(id = R.string.countries_search_placeholder)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (notFound) {
                Text(stringResource(id = R.string.message_nothing_found), color = MaterialTheme.colorScheme.error)
            } else {
                countries.forEach { country ->
                    CountryFragment(country = country)
                }
            }
        }
    }
}

suspend fun fetchResponse(urlString: String): String? {
    return withContext(Dispatchers.IO) {
        try {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 5000
            connection.readTimeout = 5000
            connection.connect()
            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                connection.inputStream.bufferedReader().use { it.readText() }
            } else null
        } catch (e: Exception) {
            null
        }
    }
}

data class Country(val name: String, val capital: String, val flagUrl: String)

suspend fun loadPicture(url: String): Bitmap? {
    return withContext(Dispatchers.IO) {
        try {
            URL(url).openStream().use { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }
        } catch (e: Exception) {
            null
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CountriesAppPreview() {
    FlightwiseTheme {
        CountriesApp()
    }
}
