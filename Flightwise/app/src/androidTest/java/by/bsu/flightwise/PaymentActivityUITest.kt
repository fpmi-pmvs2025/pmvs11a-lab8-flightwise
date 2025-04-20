package by.bsu.flightwise

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import by.bsu.flightwise.R
import by.bsu.flightwise.ui.activities.PaymentActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PaymentActivityUITest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<PaymentActivity>()

    @Test
    fun paymentScreen_displaysUIElements() {
        val activity = composeTestRule.activity
        val paymentHeader = activity.getString(R.string.payment_header)
        val paymentPaypal = activity.getString(R.string.payment_paypal)
        val paymentCard = activity.getString(R.string.payment_card)

        composeTestRule.onNodeWithText(paymentHeader).assertIsDisplayed()
        composeTestRule.onNodeWithText(paymentPaypal).assertIsDisplayed()
        composeTestRule.onNodeWithText(paymentCard).assertIsDisplayed()
    }
}
