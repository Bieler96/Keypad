package de.bieler.keypad

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import de.bieler.keypad.components.Keypad
import de.bieler.keypad.ui.theme.ErrorContainer
import de.bieler.keypad.ui.theme.ErrorText
import de.bieler.keypad.ui.theme.KeypadTheme
import de.bieler.keypad.ui.theme.SuccessContainer
import de.bieler.keypad.ui.theme.SuccessText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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
                                        snackbarHostState.showSnackbar(message = "Correct PIN", withDismissAction = true)
                                    }
                                } else {
                                    containerColor.value = ErrorContainer
                                    textColor.value = ErrorText
                                    scope.launch {
                                        snackbarHostState.showSnackbar(message = "Incorrect PIN", withDismissAction = true)
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

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CustomSnackbar(
    snackbarHostState: SnackbarHostState,
    containerColor: Color,
    textColor: Color
) {
    SnackbarHost(hostState = snackbarHostState) { data ->
        Snackbar(
            snackbarData = data,
            containerColor = containerColor,
            contentColor = textColor,
            actionColor = textColor,
            actionContentColor = textColor,
            dismissActionContentColor = textColor
        )
    }
}