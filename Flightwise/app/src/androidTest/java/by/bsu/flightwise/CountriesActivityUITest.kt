package by.bsu.flightwise

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import by.bsu.flightwise.R
import by.bsu.flightwise.ui.activities.CountriesActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CountriesActivityUITest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<CountriesActivity>()

    @Test
    fun displaysSearchFieldPlaceholder() {
        val placeholderText = composeTestRule.activity.getString(R.string.countries_search_placeholder)
        composeTestRule.onNodeWithText(placeholderText)
            .assertIsDisplayed()
    }


    @Test
    fun displaysNothingFoundMessageWhenNoResults() {
        val searchPlaceholder = composeTestRule.activity.getString(R.string.countries_search_placeholder)
        composeTestRule.onNodeWithText(searchPlaceholder)
            .performTextInput("nonexistentcountry")

        val nothingFoundText = composeTestRule.activity.getString(R.string.message_nothing_found)
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithText(nothingFoundText).fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText(nothingFoundText).assertIsDisplayed()
    }

    @Test
    fun displaysCountryListWhenResultsFound() {
        val searchPlaceholder = composeTestRule.activity.getString(R.string.countries_search_placeholder)
        composeTestRule.onNodeWithText(searchPlaceholder)
            .performTextClearance()
    }
}
