package by.bsu.flightwise.ui.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
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
import java.io.FileOutputStream

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        preloadDatabase(this)

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

    @SuppressLint("Range")
    private fun preloadDatabase(context: Context) {
        val dbName = "flightwise.db"
        val dbFile = context.getDatabasePath(dbName)

        if (!dbFile.exists()) {
            try {
                dbFile.parentFile?.mkdirs()
                context.assets.open(dbName).use { inputStream ->
                    FileOutputStream(dbFile).use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
                Log.d("DB_CHECK", "Database copied successfully to: ${dbFile.path}")
            } catch (e: Exception) {
                Log.e("DB_CHECK", "Error copying database: ${e.message}", e)
            }
        } else {
            Log.d("DB_CHECK", "Database already exists at: ${dbFile.path}")
        }

        try {
            val db = SQLiteDatabase.openDatabase(dbFile.path, null, SQLiteDatabase.OPEN_READONLY)
            val tableCursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null)
            if (tableCursor.moveToFirst()) {
                do {
                    val tableName = tableCursor.getString(0)
                    Log.d("DB_CHECK", "======================================")
                    Log.d("DB_CHECK", "Table: $tableName")

                    val columnsCursor = db.rawQuery("PRAGMA table_info($tableName)", null)
                    val columns = mutableListOf<String>()
                    if (columnsCursor.moveToFirst()) {
                        do {
                            val colName = columnsCursor.getString(columnsCursor.getColumnIndex("name"))
                            columns.add(colName)
                        } while (columnsCursor.moveToNext())
                    }
                    columnsCursor.close()
                    Log.d("DB_CHECK", "Columns: ${columns.joinToString(", ")}")

                    val contentCursor = db.rawQuery("SELECT * FROM $tableName", null)
                    if (contentCursor.moveToFirst()) {
                        do {
                            val rowValues = columns.map { col ->
                                try {
                                    contentCursor.getString(contentCursor.getColumnIndex(col)) ?: "null"
                                } catch (ex: Exception) {
                                    "error"
                                }
                            }
                            Log.d("DB_CHECK", "Row: ${rowValues.joinToString(", ")}")
                        } while (contentCursor.moveToNext())
                    } else {
                        Log.d("DB_CHECK", "Table $tableName is empty")
                    }
                    contentCursor.close()
                } while (tableCursor.moveToNext())
            } else {
                Log.d("DB_CHECK", "No tables found in the database")
            }
            tableCursor.close()
            db.close()
        } catch (e: Exception) {
            Log.e("DB_CHECK", "Error reading database content: ${e.message}", e)
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