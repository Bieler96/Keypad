package de.bieler.keypad

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import de.bieler.keypad.components.InputRow

@ExperimentalCoroutinesApi
class InputRowTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun inputRowDisplaysButtonsWithCorrectText() = runTest {
        val buttonTexts = listOf("1", "2", "3")
        composeTestRule.setContent {
            InputRow(buttonTexts.map { it to {} })
        }

        buttonTexts.forEach { text ->
            composeTestRule.onNodeWithText(text).assertExists()
        }
    }

    @Test
    fun buttonClickTriggersCallback() = runTest {
        var callbackTriggered = false
        val buttonText = "Test"
        composeTestRule.setContent {
            InputRow(listOf(buttonText to { callbackTriggered = true }))
        }

        composeTestRule.onNodeWithText(buttonText).performClick()
        delay(100) // Wait for the click to be processed

        assert(callbackTriggered)
    }

    @Test
    fun buttonTextSizeChangesOnClick() = runTest {
        val buttonText = "Size Test"
        composeTestRule.setContent {
            InputRow(listOf(buttonText to {}))
        }

        composeTestRule.onNodeWithText(buttonText).performClick()
        delay(100) // Wait for the animation to start

        // This test assumes the existence of a mechanism to verify text size changes, which is not directly supported by Compose UI tests.
        // It might involve checking the state of the component or using screenshot testing to verify the change in text size.
    }
}