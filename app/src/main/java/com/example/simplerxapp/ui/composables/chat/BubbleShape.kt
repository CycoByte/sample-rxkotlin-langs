package com.example.simplerxapp.ui.composables.chat

import androidx.compose.foundation.shape.GenericShape
import androidx.compose.ui.geometry.Rect

fun ChatBubbleShape(cornerRadius: Float, tailWidth: Float, tailHeight: Float) = GenericShape { size, _ ->
    val width = size.width
    val height = size.height

    // Start from the top-left corner
    moveTo(cornerRadius, 0f)
    lineTo(width - cornerRadius, 0f)

    // Top-right corner arc
    arcTo(
        rect = Rect(width - 2 * cornerRadius, 0f, width, 2 * cornerRadius),
        startAngleDegrees = 270f,
        sweepAngleDegrees = 90f,
        forceMoveTo = false
    )

    lineTo(width, height - cornerRadius)

    // Bottom-right corner arc
    arcTo(
        rect = Rect(width - 2 * cornerRadius, height - 2 * cornerRadius, width, height),
        startAngleDegrees = 0f,
        sweepAngleDegrees = 90f,
        forceMoveTo = false
    )

    lineTo(cornerRadius, height)

    // Bottom-left corner arc with tail
    arcTo(
        rect = Rect(0f, height - 2 * cornerRadius, 2 * cornerRadius, height),
        startAngleDegrees = 90f,
        sweepAngleDegrees = 90f, //45
        forceMoveTo = false
    )

    lineTo(0f, cornerRadius)

    // Top-left corner arc
    arcTo(
        rect = Rect(0f, 0f, 2 * cornerRadius, 2 * cornerRadius),
        startAngleDegrees = 180f,
        sweepAngleDegrees = 90f,
        forceMoveTo = false
    )
    //    tailWidth
    lineTo(tailWidth, height - tailHeight) // Tail start
    lineTo(0f, height * 0.6f) // Tail end

    close()
}