package com.example.simplerxapp.ui.composables.chat

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.simplerxapp.ui.chat.models.ChatMessage
import com.example.simplerxapp.ui.chat.models.ReceivedMessageActions
import com.example.simplerxapp.ui.chat.models.ReceivedMessageActions.Companion.getImageResource
import com.example.simplerxapp.ui.theme.CL_ReceivedBubbleColor
import com.example.simplerxapp.ui.theme.ChatBubbleCornerRadius
import com.example.simplerxapp.ui.theme.ChatBubbleTextStyle

@Composable
fun ReceiverBubbleView(
    modifier: Modifier,
    message: ChatMessage.Received,
    color: Color = CL_ReceivedBubbleColor,
    cornerRadius: Dp = ChatBubbleCornerRadius,
    isLast: Boolean,
    contentPadding: PaddingValues,
    onAction: (ReceivedMessageActions) -> Unit,
    onSuggestionClicked: (ChatMessage.Feedback.Suggestion) -> Unit,
) {

    val radiusPx = with(LocalDensity.current) {
        cornerRadius.toPx()
    }

    Column(
        modifier = Modifier
            .padding(end = if (message.actions.isEmpty()) 45.dp else 8.dp)
            .fillMaxWidth()
            .then(modifier),
    ) {
        Row(

        ) {
            Surface(
                modifier = Modifier
                    .padding(contentPadding)
                    .weight(1f, fill = false),
                shape = RoundedCornerShape(
                    topStart = radiusPx,
                    topEnd = radiusPx,
                    bottomStart = if (isLast) 0f else radiusPx,
                    bottomEnd = radiusPx
                ),
                color = color
            ) {
                Column(
                    modifier = Modifier
                        .padding(6.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Start),
                        text = message.body,
                        style = ChatBubbleTextStyle
                    )

                    Row(
                        modifier = Modifier
                            .align(Alignment.Start),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            modifier = Modifier.offset(y = 2.dp),
                            text = message.time,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray
                        )
                    }
                }
            }
            AnimatedVisibility(message.actions.isNotEmpty()) {
                ActionsGridView(
                    modifier = Modifier.weight(0.2f),
                    messageActions = message.actions,
                    onSelectedAction = onAction
                )
            }
        }

        message.suggestions.forEach { suggestion ->
            SuggestionView(
                modifier = Modifier.padding(contentPadding),
                suggestion = suggestion,
                onAction = {
                    onSuggestionClicked(suggestion)
                }
            )
        }
    }
}

@Composable
private fun SuggestionView(
    modifier: Modifier = Modifier,
    suggestion: ChatMessage.Feedback.Suggestion,
    onAction: () -> Unit
) {
    Surface(
        modifier = Modifier
            .defaultMinSize(minWidth = 160.dp, minHeight = 36.dp)
            .then(modifier),
        onClick = onAction,
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 0.dp,
        shadowElevation = 6.dp
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = suggestion.message
        )
    }
}

@Composable
private fun ActionsGridView(
    modifier: Modifier,
    messageActions: List<ReceivedMessageActions>,
    onSelectedAction: (ReceivedMessageActions) -> Unit,
) {
    Row(
        modifier = modifier,
//        columns = GridCells.FixedSize(30.dp),
//        contentPadding = PaddingValues()
    ) {
        messageActions.forEach { action ->
            val icon = remember(action) {
                action.getImageResource()
            }
            IconButton(
                modifier = Modifier.size(32.dp),
                onClick = {
                    onSelectedAction(action)
                }
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(icon),
                    contentDescription = "dolphin-action",
                    tint = Color.DarkGray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReceiverCharPreview() {
    val items = listOf(
        ChatMessage.Received(
            id = "2",
            order = 1,
            body = "an error!",
            time = "12:00",
        ),
        ChatMessage.Received(
            id = "1",
            order = 0,
            body = "this is a test",
            time = "12:00",
            actions = listOf(
                ReceivedMessageActions.LISTEN,
                ReceivedMessageActions.TRANSLATION,
            )
        ),
        ChatMessage.Received(
            id = "1",
            order = 0,
            body = "this is a very large test to see how this will fit in the view with multiple lines, here the used is writing way too much.",
            time = "12:00",
            actions = listOf(
                ReceivedMessageActions.LISTEN,
                ReceivedMessageActions.TRANSLATION,
                ReceivedMessageActions.COPY,
                ReceivedMessageActions.MORE_INFO
            ),
            suggestions = listOf(
                ChatMessage.Feedback.Suggestion(
                    id = "1",
                    message = "Rainbows"
                ),
                ChatMessage.Feedback.Suggestion(
                    id = "2",
                    message = "Unicorns"
                )
            )
        ),
    )
    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        items.forEachIndexed { index, item ->
            ReceiverBubbleView(
                modifier = Modifier,
                message = item,
                isLast = index == items.lastIndex,
                contentPadding = PaddingValues(start = 16.dp),
                onAction = {
                    println("Perform action: $it")
                },
                onSuggestionClicked = {
                    println("Perform action: $it")
                }
            )
        }
    }
}