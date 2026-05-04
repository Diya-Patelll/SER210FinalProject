package com.example.ser210finalproject

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginScreenInstrumentedTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun invalidEmail_showsLoginErrorMessage() {
        composeTestRule.onNodeWithTag("login_email_field")
            .performTextInput("diya@gmail.com")

        composeTestRule.onNodeWithTag("login_password_field")
            .performTextInput("password123")

        composeTestRule.onNodeWithTag("login_submit_button")
            .performClick()

        composeTestRule.onNodeWithTag("login_error_text")
            .assertIsDisplayed()
    }
}
