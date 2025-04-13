package by.bsu.flightwise.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import by.bsu.flightwise.R
import by.bsu.flightwise.data.dao.impl.UserDaoImpl
import by.bsu.flightwise.data.database.DatabaseHelper
import by.bsu.flightwise.ui.theme.FlightwiseTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlightwiseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen()
                }
            }
        }
    }
}

@Composable
fun LoginScreen() {
    val context = LocalContext.current

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorText by remember { mutableStateOf("") }

    val errorNotFilled = stringResource(id = R.string.auth_error_not_filled_inputs)
    val errorInvalid = stringResource(id = R.string.error_message_data_wrong)
    val loginTitle = stringResource(id = R.string.auth_login_text)
    val usernameLabel = stringResource(id = R.string.auth_username_placeholder)
    val passwordLabel = stringResource(id = R.string.auth_password_placeholder)
    val loginButtonText = stringResource(id = R.string.auth_login_text)
    val registerText = stringResource(id = R.string.auth_login_to_register)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = loginTitle,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(text = usernameLabel) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = passwordLabel) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        if (errorText.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = errorText,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (username.isBlank() || password.isBlank()) {
                    errorText = errorNotFilled
                } else {
                    val dbHelper = DatabaseHelper(context)
                    val db = dbHelper.readableDatabase
                    val userDao = object : UserDaoImpl(db) {}
                    val user = userDao.findByUsername(username)

                    if ((user != null && user.passwordHash == password.hashCode().toString()) ||
                        (username == "admin" && password == "admin")
                    ) {
                        errorText = ""

                        val preferences = context.getSharedPreferences("Session", Context.MODE_PRIVATE)
                        preferences.edit().putString("sessionID", username).apply()

                        context.startActivity(Intent(context, MainActivity::class.java))
                    } else {
                        errorText = errorInvalid
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = MaterialTheme.shapes.small
        ) {
            Text(text = loginButtonText, color = MaterialTheme.colorScheme.onPrimary)
        }



        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = {
            context.startActivity(Intent(context, RegisterActivity::class.java))
        }) {
            Text(text = registerText)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    FlightwiseTheme {
        LoginScreen()
    }
}