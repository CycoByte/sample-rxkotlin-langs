package com.example.simplerxapp.ui.composables.containers

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.simplerxapp.ui.UIState
import kotlinx.coroutines.launch

@Composable
fun MainContentHolder(
    modifier: Modifier,
    topBar: @Composable () -> Unit,
    uiState: UIState<*>,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    val sHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState) {
        when {
            uiState.isError -> scope.launch {
                sHostState.showSnackbar((uiState as UIState.Error).errorMessage)
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = topBar,
        snackbarHost = {
            SnackbarHost(sHostState)
        }
    ) {
        LoadableContentHolder(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            uiState = uiState,
            content = content
        )
    }
}