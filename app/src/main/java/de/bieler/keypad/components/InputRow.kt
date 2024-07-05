package de.bieler.keypad.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun InputRow(
    list: List<Pair<String, () -> Unit>>
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        list.forEach { (text, onClick) ->
            val interactionSource = remember { MutableInteractionSource() }
            var isClicked by remember { mutableStateOf(false) }
            val textSize by animateFloatAsState(
                targetValue = if (isClicked) MaterialTheme.typography.displayMedium.fontSize.value else MaterialTheme.typography.displaySmall.fontSize.value,
                label = ""
            )

            LaunchedEffect(isClicked) {
                launch {
                    delay(50)
                    isClicked = false
                }
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1.5f)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        isClicked = true
                        onClick()
                    }
            ) {
                Text(text = text, fontSize = textSize.sp)
            }
        }
    }
}