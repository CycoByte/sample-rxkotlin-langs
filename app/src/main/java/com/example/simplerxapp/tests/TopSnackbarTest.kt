package com.example.simplerxapp.tests

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopSnackbarExample() {

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val desnsity = LocalDensity.current

    // Scaffold with a custom SnackbarHost
    Scaffold(
//        snackbarHost = {
//            Box( // Use Box to align the SnackbarHost
//                modifier = Modifier
//                    .fillMaxSize()
//            ) {
//
//            }
//        },
        topBar = {
            TopAppBar(
                title = { Text("Top Snackbar Example") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) { padding ->
        // Main content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = "This is a top Snackbar!",
                            actionLabel = "Dismiss",
                            duration = SnackbarDuration.Short
                        )
                    }
                },
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text("Show Snackbar")
            }

            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .padding(top = TopAppBarDefaults.windowInsets.getTop(desnsity).dp)
                    .align(Alignment.TopCenter),
                snackbar = {
//                    SnackbarData(
//                        message = it.message,
//                        actionLabel = it.actionLabel,
//                        onAction = { snackbarHostState.currentSnackbarData?.dismiss() }
//                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SnackbarPreview() {
    TopSnackbarExample()
}