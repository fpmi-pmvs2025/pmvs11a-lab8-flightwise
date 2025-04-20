package by.bsu.flightwise

import android.content.Context
import android.content.Intent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import by.bsu.flightwise.R
import by.bsu.flightwise.ui.activities.ProfileActivity
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProfileActivityUITest {

    companion object {
        @BeforeClass
        @JvmStatic
        fun setUpClass() {
            val context = ApplicationProvider.getApplicationContext<Context>()
            context.getSharedPreferences("Session", Context.MODE_PRIVATE).edit().apply {
                putString("sessionID", "TestUser")
                putString("userName", "TestUser")
                putLong("passengerId", 1234L)
                commit()
            }
        }
    }

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ProfileActivity>()

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
    }

    @Test
    fun displaysUserName() {
        composeTestRule.onNodeWithText("TestUser")
            .assertIsDisplayed()
    }

    @Test
    fun displaysUserRole() {
        val expectedRole = composeTestRule.activity.getString(R.string.profile_passenger_role)
        composeTestRule.onNodeWithText(expectedRole)
            .assertIsDisplayed()
    }

    @Test
    fun displaysYourTicketsLabel() {
        val yourTicketsLabel = composeTestRule.activity.getString(R.string.profile_tickets_text)
        composeTestRule.onNodeWithText(yourTicketsLabel)
            .assertIsDisplayed()
    }

    @Test
    fun displaysNothingFoundMessageWhenNoTickets() {
        val nothingFoundMessage = composeTestRule.activity.getString(R.string.message_nothing_found)
        composeTestRule.onNodeWithText(nothingFoundMessage)
            .assertIsDisplayed()
    }
}
