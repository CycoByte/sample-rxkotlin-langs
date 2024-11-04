package com.example.simplerxapp.ui.composables.chat

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.simplerxapp.ui.theme.CL_ReceivedBubbleColor
import com.example.simplerxapp.ui.theme.ChatBubbleCornerRadius
import com.example.simplerxapp.ui.theme.SimpleRxAppTheme

@Composable
fun ChatDotLoadingIndicator(
    dotSize: Dp = 10.dp,
    dotSpacing: Dp = 8.dp,
    animationDurationMillis: Int = 550,
    background: Color = CL_ReceivedBubbleColor,
    cornerRadius: Dp = ChatBubbleCornerRadius,
) {
    // Define animation scale transition
    val transition = rememberInfiniteTransition(label = "chatLoadAnimation")
    val dotScale = listOf(
        transition.animateFloat(
            initialValue = 0.5f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = animationDurationMillis, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse,
                initialStartOffset = StartOffset(0)
            ), label = "1st"
        ),
        transition.animateFloat(
            initialValue = 0.5f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = animationDurationMillis, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse,
                initialStartOffset = StartOffset(200)
            ), label = "2nd"
        ),
        transition.animateFloat(
            initialValue = 0.5f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = animationDurationMillis, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse,
                initialStartOffset = StartOffset(400)
            ), label = "3rd"
        )
    )

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(cornerRadius))
            .background(background)
            .padding(16.dp)
            .wrapContentSize(),
        horizontalArrangement = Arrangement.spacedBy(dotSpacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        dotScale.forEach { scale ->
            Box(
                modifier = Modifier
                    .size(dotSize)
                    .scale(scale.value)
                    .alpha(scale.value)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = MaterialTheme.shapes.small
                    )
            )
        }
    }
}

@Preview
@Composable
private fun LoaderPreview() {

    SimpleRxAppTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            ChatDotLoadingIndicator()
        }
    }
}