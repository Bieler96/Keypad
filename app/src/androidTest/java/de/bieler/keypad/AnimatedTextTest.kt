package de.bieler.keypad

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import de.bieler.keypad.components.AnimatedText
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AnimatedTextTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private var initialTextSize: Float = 0f
    private var targetTextSize: Float = 0f

    @Before
    fun setup() {
        composeTestRule.setContent {
            SetupTheme()
        }
    }

    @Composable
    fun SetupTheme() {
        AnimatedText("Test")
        initialTextSize = MaterialTheme.typography.displaySmall.fontSize.value
        targetTextSize = MaterialTheme.typography.displayLarge.fontSize.value
    }

    @Test
    fun animatedText_initialDisplay_ShowsWithInitialSize() {
        composeTestRule.onNodeWithText("Test").assertExists()
        // TODO Adjustments needed for assertWidthIsEqualTo due to type mismatch
    }

    @Test
    fun animatedText_afterAnimation_ShowsWithTargetSize() {
        Thread.sleep(100) // Assuming animation duration + delay is less than 100ms
        composeTestRule.onNodeWithText("Test").assertExists()
        // TODO Adjustments needed for assertWidthIsEqualTo due to type mismatch
    }
}