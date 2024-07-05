package de.bieler.keypad.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import de.bieler.keypad.utils.Vibrate.errorVibrate
import de.bieler.keypad.utils.Vibrate.vibrate

/**
 * A customizable keypad composable function that allows input of a numeric PIN code.
 *
 * @param maxDigits The maximum number of digits allowed in the PIN. Defaults to 4.
 * @param onConfirm A callback function that is invoked when the PIN is confirmed. It provides the entered PIN and a function to clear the PIN as parameters.
 * @param autoConfirm Automatically confirms the PIN once the max number of digits is reached. Defaults to false.
 * @param autoClearAfterConfirm Automatically clears the PIN after confirmation. Useful for one-time use scenarios. Defaults to false.
 */
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

    /**
     * Clears the current PIN and the list of entered digits.
     */
    fun onClearClick() {
        pin = ""
        digits = emptyList()
    }

    /**
     * Handles digit button clicks.
     * Adds the digit to the PIN and list of digits if the maxDigits limit has not been reached.
     * Triggers vibration feedback and optionally confirms the PIN if autoConfirm is enabled.
     *
     * @param digit The digit that was clicked.
     */
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

    /**
     * Removes the last digit from the PIN and the list of digits.
     */
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