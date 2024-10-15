package com.example.simplerxapp.ui.composables.containers

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import com.example.simplerxapp.ui.UIState

@Composable
fun LoadableContentHolder(
    modifier: Modifier,
    uiState: UIState<*>,
    content:@Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .then(modifier)
    ) {
        AnimatedVisibility(
            visible = uiState.isLoading,
            modifier = Modifier
                .zIndex(200F)
                .align(Alignment.Center),
        ) {
            CircularProgressIndicator()
        }

        Box(
            modifier = Modifier
                .clickable(enabled = uiState.isLoading) {  }
                .matchParentSize()
        ) {
            content()
        }
    }
}