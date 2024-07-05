package de.bieler.keypad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import de.bieler.keypad.components.CustomSnackbar
import de.bieler.keypad.components.Keypad
import de.bieler.keypad.ui.theme.ErrorContainer
import de.bieler.keypad.ui.theme.ErrorText
import de.bieler.keypad.ui.theme.KeypadTheme
import de.bieler.keypad.ui.theme.SuccessContainer
import de.bieler.keypad.ui.theme.SuccessText
import kotlinx.coroutines.launch

/**
 * MainActivity is the entry point of the application, setting up the UI and its components.
 *
 * This activity sets up the main content view using Jetpack Compose. It initializes the keypad component,
 * handles PIN confirmation, and displays appropriate snackbars based on the PIN validation result.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val scope = rememberCoroutineScope()
            val snackbarHostState = remember { SnackbarHostState() }
            val containerColor = remember { mutableStateOf(Color.Transparent) }
            val textColor = remember { mutableStateOf(Color.Transparent) }

            KeypadTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = {
                        CustomSnackbar(
                            snackbarHostState,
                            containerColor.value,
                            textColor.value
                        )
                    },
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        Keypad(
                            autoConfirm = false,
                            autoClearAfterConfirm = true,
                            onConfirm = { pin, _ ->
                                if (pin == "1111") {
                                    containerColor.value = SuccessContainer
                                    textColor.value = SuccessText
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = "Correct PIN",
                                            withDismissAction = true
                                        )
                                    }
                                } else {
                                    containerColor.value = ErrorContainer
                                    textColor.value = ErrorText
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = "Incorrect PIN",
                                            withDismissAction = true
                                        )
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}