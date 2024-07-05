package de.bieler.keypad.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Displays an animated text that changes its size when first displayed.
 *
 * This composable function creates a text element that initially appears larger or smaller
 * and then animates to its target size. The size transition is triggered upon initial display,
 * simulating a click effect without actual user interaction.
 *
 * @param text The text content to be displayed.
 */
@Composable
fun AnimatedText(text: String) {
    var isClicked by remember { mutableStateOf(true) }
    val textSize by animateFloatAsState(
        targetValue = if (isClicked) MaterialTheme.typography.displaySmall.fontSize.value else MaterialTheme.typography.displayLarge.fontSize.value,
        label = ""
    )

    LaunchedEffect(isClicked) {
        launch {
            delay(0)
            isClicked = false
        }
    }

    Text(text = text, fontSize = textSize.sp)
}