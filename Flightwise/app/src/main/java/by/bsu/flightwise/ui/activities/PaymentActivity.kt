package by.bsu.flightwise.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import by.bsu.flightwise.data.entity.Passenger
import by.bsu.flightwise.data.entity.Ticket
import by.bsu.flightwise.ui.fragments.FooterFragment
import by.bsu.flightwise.ui.fragments.HeaderFragment
import by.bsu.flightwise.ui.theme.FlightwiseTheme

class PaymentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val passengers = intent.getParcelableArrayListExtra<Passenger>("passengers") ?: arrayListOf()
        val ticket = intent.getParcelableExtra<Ticket>("ticket")

        setContent {
            FlightwiseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PaymentScreen(ticket = ticket, passengers = passengers)
                }
            }
        }
    }
}

@Composable
fun PaymentScreen(ticket: Ticket?, passengers: List<Passenger>?) {

    Box(modifier = Modifier.fillMaxSize()) {

        val context = LocalContext.current

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
                .align(Alignment.Center)
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Choose payment",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = 16.dp),
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val youtubeIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://youtu.be/dQw4w9WgXcQ?si=U0d2p_CD8-mOiVfg")
                    )
                    context.startActivity(youtubeIntent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(48.dp),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Paypal")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val youtubeIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://youtu.be/dQw4w9WgXcQ?si=U0d2p_CD8-mOiVfg")
                    )
                    context.startActivity(youtubeIntent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(48.dp),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Card")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PaymentScreenPreview() {
    FlightwiseTheme {
        PaymentScreen(null, null)
    }
}
