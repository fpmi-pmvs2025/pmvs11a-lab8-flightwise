package by.bsu.flightwise

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import by.bsu.flightwise.R
import by.bsu.flightwise.ui.activities.ProfileActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProfileActivityUITest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ProfileActivity>()

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val preferences = context.getSharedPreferences("Session", Context.MODE_PRIVATE)
        preferences.edit().apply {
            putString("sessionID", "TestUser")
            putString("userName", "TestUser")
            putLong("passengerId", 1234L)
            commit()
        }
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

}
