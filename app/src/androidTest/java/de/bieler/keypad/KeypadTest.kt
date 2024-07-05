package de.bieler.keypad

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import de.bieler.keypad.components.Keypad
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class KeypadTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun pinUpdatesOnDigitClick() = runTest {
        var pinValue = ""
        composeTestRule.setContent {
            Keypad(onConfirm = { pin, _ -> pinValue = pin })
        }

        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("Continue").performClick()
        assert(pinValue == "1")
    }

    @Test
    fun pinDoesNotExceedMaxDigits() = runTest {
        var pinValue = ""
        composeTestRule.setContent {
            Keypad(maxDigits = 4, onConfirm = { pin, _ -> pinValue = pin })
        }

        "12345".forEach { digit ->
            composeTestRule.onNodeWithText(digit.toString()).performClick()
        }
        assert(pinValue.length <= 4)
    }

    @Test
    fun autoConfirmTriggersOnMaxDigits() = runTest {
        var confirmCalled = false
        composeTestRule.setContent {
            Keypad(maxDigits = 4, autoConfirm = true, onConfirm = { _, _ -> confirmCalled = true })
        }

        "1234".forEach { digit ->
            composeTestRule.onNodeWithText(digit.toString()).performClick()
        }
        assert(confirmCalled)
    }

    @Test
    fun clearButtonResetsPin() = runTest {
        var pinValue = "1234"
        composeTestRule.setContent {
            Keypad(onConfirm = { pin, clear -> pinValue = pin; clear() })
        }

        composeTestRule.onNodeWithText("C").performClick()
        composeTestRule.onNodeWithText("Continue").performClick()
        assert(pinValue.isEmpty())
    }

    @Test
    fun backspaceRemovesLastDigit() = runTest {
        var pinValue = ""
        composeTestRule.setContent {
            Keypad(onConfirm = { pin, _ -> pinValue = pin })
        }

        "123".forEach { digit ->
            composeTestRule.onNodeWithText(digit.toString()).performClick()
        }
        composeTestRule.onNodeWithText("⌫").performClick()
        composeTestRule.onNodeWithText("Continue").performClick()
        assert(pinValue == "12")
    }

    @Test
    fun continueButtonAppearsWhenAutoConfirmIsDisabled() = runTest {
        composeTestRule.setContent {
            Keypad(
                autoConfirm = false,
                onConfirm = { _, _ -> }
            )
        }

        composeTestRule.onNodeWithText("Continue").assertExists()
    }
}