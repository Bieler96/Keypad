package de.bieler.keypad.components

import android.annotation.SuppressLint
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Displays a custom Snackbar with configurable colors.
 *
 * This composable function creates a Snackbar that can be customized with specific colors for its container and text content.
 * It is designed to be used within a SnackbarHost, allowing for consistent styling across different parts of an application.
 *
 * @param snackbarHostState The state object controlling the snackbar. This is typically managed by a SnackbarHost.
 * @param containerColor The color of the snackbar's background container.
 * @param textColor The color of the text content and actions within the snackbar.
 * @suppress "CoroutineCreationDuringComposition" This suppression is necessary due to the initialization of coroutines within the composable function, which is generally discouraged. It is used here for demonstration purposes.
 */
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