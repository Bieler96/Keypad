package de.bieler.keypad.components

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Keypad(
    maxDigits: Int = 4,
    onConfirm: (String, () -> Unit) -> Unit,
    autoConfirm: Boolean = false,
    autoClearAfterConfirm: Boolean = false
) {
    val context = LocalContext.current
    var pin by remember { mutableStateOf("") }
    var digits by remember { mutableStateOf(emptyList<String>()) }

    fun onClearClick() {
        pin = ""
        digits = emptyList()
    }

    fun onDigitClick(digit: String) {
        if (digits.size >= maxDigits) {
            errorVibrate(context)
            return
        }

        pin += digit
        digits += digit
        vibrate(context)

        if (autoConfirm && digits.size == maxDigits) {
            onConfirm(pin, ::onClearClick)

            if (autoClearAfterConfirm) {
                onClearClick()
            }
        }
    }

    fun onRemoveClick() {
        if (digits.isNotEmpty()) {
            pin = pin.dropLast(1)
            digits = digits.dropLast(1)
        }
    }

    val row1 = listOf(
        Pair("1") { onDigitClick("1") },
        Pair("2") { onDigitClick("2") },
        Pair("3") { onDigitClick("3") },
    )
    val row2 = listOf(
        Pair("4") { onDigitClick("4") },
        Pair("5") { onDigitClick("5") },
        Pair("6") { onDigitClick("6") },
    )
    val row3 = listOf(
        Pair("7") { onDigitClick("7") },
        Pair("8") { onDigitClick("8") },
        Pair("9") { onDigitClick("9") },
    )
    val row4 = listOf(
        Pair("⌫") { onRemoveClick() },
        Pair("0") { onDigitClick("0") },
        Pair("C") { onClearClick() }
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.4f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (digit in digits) {
                    AnimatedText(digit)
                }
            }
        }
        Column(
            modifier = Modifier
                .weight(0.6f)
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            InputRow(row1)
            InputRow(row2)
            InputRow(row3)
            InputRow(row4)
            if (!autoConfirm) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onConfirm(pin, ::onClearClick)

                        if (autoClearAfterConfirm) {
                            onClearClick()
                        }
                    }
                ) {
                    Text("Continue")
                }
            }
        }
    }
}

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

fun vibrate(context: Context) {
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        @Suppress("DEPRECATION")
        vibrator.vibrate(50)
    }
}

fun errorVibrate(context: Context) {
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        @Suppress("DEPRECATION")
        vibrator.vibrate(150)
    }
}

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