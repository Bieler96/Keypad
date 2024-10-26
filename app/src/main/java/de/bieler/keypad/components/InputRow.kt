package de.bieler.keypad.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Displays a row of buttons for the keypad, with each button having an animation effect on click.
 *
 * This composable function generates a row of buttons for the keypad UI. Each button is represented by a text label
 * and executes a provided callback function when clicked. The buttons also feature a subtle animation effect that
 * temporarily changes the text size to indicate a click.
 *
 * @param list A list of pairs, where each pair consists of a button's text label and the callback function to be executed on click.
 * @param withBackground A boolean flag indicating whether the buttons should have a background color. Defaults to false.
 */
@Composable
fun InputRow(
	list: List<Pair<String, () -> Unit>>,
	withBackground: Boolean = false
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
					.aspectRatio(1f)
					.clickable(
						interactionSource = interactionSource,
						indication = null
					) {
						isClicked = true
						onClick()
					}
			) {
				if (!withBackground) {
					Text(text = text, fontSize = textSize.sp)
				} else {
					Box(
						modifier = Modifier
							.size(80.dp)
							.clip(MaterialTheme.shapes.extraLarge)
							.background(MaterialTheme.colorScheme.surfaceContainer)
							.clickable(
								interactionSource = interactionSource,
								indication = null
							) {
								isClicked = true
								onClick()
							}
					) {
						Text(
							text = text,
							fontSize = textSize.sp,
							color = MaterialTheme.colorScheme.onSurface,
							modifier = Modifier.align(Alignment.Center)
						)
					}
				}
			}
		}
	}
}