package com.example.simplerxapp.ui.composables.chat

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.simplerxapp.R
import com.example.simplerxapp.ui.chat.models.ChatMessage
import com.example.simplerxapp.tools.ComposeTools.noRippleClickable
import com.example.simplerxapp.ui.chat.models.SentMessageStatus
import com.example.simplerxapp.ui.chat.models.SentMessageStatus.Companion.getImageResource
import com.example.simplerxapp.ui.theme.CL_Error300
import com.example.simplerxapp.ui.theme.CL_SentBubbleColor
import com.example.simplerxapp.ui.theme.ChatBubbleCornerRadius
import com.example.simplerxapp.ui.theme.ChatBubbleTextStyle
import com.example.simplerxapp.ui.theme.Cl_Error500
import com.example.simplerxapp.ui.theme.Cl_Success300
import com.example.simplerxapp.ui.theme.Cl_Success500
import com.example.simplerxapp.ui.theme.Cl_Warning500

@Composable
fun SenderBubbleView(
    modifier: Modifier,
    message: ChatMessage.Sent,
    color: Color = CL_SentBubbleColor,
    cornerRadius: Dp = ChatBubbleCornerRadius,
    isLast: Boolean,
    onViewFeedbackAction: (ChatMessage.Feedback) -> Unit
) {

    val radiusPx = with(LocalDensity.current) {
        cornerRadius.toPx()
    }

    val statusIconResource by remember(message.status) {
        derivedStateOf {
            message.status.getImageResource()
        }
    }

    val animatedStatusColor by animateColorAsState(
        label = "colAnim",
        targetValue = when (message.status) {
            SentMessageStatus.SENT_RECEIVED -> Cl_Success300
            SentMessageStatus.FAILED -> CL_Error300
            SentMessageStatus.NONE,
            SentMessageStatus.SENDING -> Color.LightGray
            SentMessageStatus.SENT -> Color.DarkGray
        }
    )

    Column(
        modifier = Modifier
            .padding(start = 45.dp)
            .fillMaxWidth()
            .then(modifier),
        horizontalAlignment = Alignment.End,
    ) {
        Surface(
            modifier = Modifier,
            shape = RoundedCornerShape(
                topStart = radiusPx,
                topEnd = radiusPx,
                bottomStart = radiusPx,
                bottomEnd = if (isLast) 0f else radiusPx,
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
                    style = ChatBubbleTextStyle,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                Row(
                    modifier = Modifier
                        .align(Alignment.End),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        modifier = Modifier.offset(y = 2.dp),
                        text = message.time,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.LightGray
                    )
                    AnimatedContent(
                        targetState = statusIconResource,
                        label = "animContent"
                    ) { iconRes ->
                        Icon(
                            modifier = Modifier
                                .padding(start = 4.dp)
                                .size(12.dp),
                            painter = painterResource(iconRes),
                            contentDescription = "status",
                            tint = animatedStatusColor
                        )
                    }
                }
            }
        }
        AnimatedContent(targetState = message.feedback, label = "feedbackAnim") { feedback ->
            when (feedback) {
                is ChatMessage.Feedback.Positive -> {
                    FeedbackRow(
                        modifier = Modifier.padding(top = 2.dp, bottom = 6.dp),
                        icon = R.drawable.baseline_check_circle_outline_24,
                        color = Cl_Success500,
                        info = stringResource(R.string.great_work)
                    )
                }
                is ChatMessage.Feedback.Wrong -> {
                    FeedbackRow(
                        modifier = Modifier
                            .padding(top = 2.dp, bottom = 6.dp)
                            .noRippleClickable{
                                onViewFeedbackAction(feedback)
                            },
                        icon = R.drawable.baseline_error_outline_24,
                        color = Cl_Error500,
                        info = feedback.shortMessage
                    )
                }
                is ChatMessage.Feedback.Suggestion -> {
                    FeedbackRow(
                        modifier = Modifier
                            .padding(top = 2.dp, bottom = 6.dp)
                            .noRippleClickable {
                                onViewFeedbackAction(feedback)
                            },
                        icon = R.drawable.baseline_error_outline_24,
                        color = Cl_Warning500,
                        info = feedback.shortMessage
                    )
                }
                else -> {}
            }
        }
    }
}


@Composable
private fun FeedbackRow(
    modifier: Modifier,
    @DrawableRes icon: Int,
    info: String,
    color: Color,
    alpha: Float = 0.8f
) {
    val current = LocalContentColor.current.copy(alpha = alpha)
    CompositionLocalProvider(LocalContentColor provides current) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.Bottom
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(icon),
                tint = color.copy(alpha = alpha),
                contentDescription = "feedback"
            )
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = info,
                style = MaterialTheme.typography.labelMedium,
                color = color
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SentBubblePreview() {

    val messages = listOf(
        ChatMessage.Sent(
            id = "2",
            order = 1,
            body = "an error!",
            time = "12:00",
            status = SentMessageStatus.FAILED,
            feedback = null
        ),
        ChatMessage.Sent(
            id = "1",
            order = 0,
            body = "this is a test",
            time = "12:00",
            status = SentMessageStatus.SENT_RECEIVED,
            feedback = ChatMessage.Feedback.Positive("fid1")
        ),
        ChatMessage.Sent(
            id = "3",
            order = 2,
            body = "this is a test",
            time = "12:05",
            status = SentMessageStatus.SENT,
            feedback = ChatMessage.Feedback.Suggestion(
                "fid2",
                "grammar error \uD83E\uDD14"
            )
        ),
        ChatMessage.Sent(
            id = "3",
            order = 2,
            body = "this is a very large test to see how this will fit in the view with multiple lines, here the used is writing way too much.",
            time = "12:05",
            status = SentMessageStatus.SENDING,
            feedback = ChatMessage.Feedback.Wrong("fid3", "invalid characters \uD83D\uDCA9")
        ),
        ChatMessage.Sent(
            id = "3",
            order = 2,
            body = "thsending",
            time = "12:05",
            status = SentMessageStatus.SENDING,
            feedback = ChatMessage.Feedback.Wrong("fid3", "invalid characters")
        )
    )

    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        messages.forEachIndexed { index, item ->
            SenderBubbleView(
                modifier = Modifier,
                message = item,
                color = CL_SentBubbleColor,
                isLast = index == messages.lastIndex,
                onViewFeedbackAction = {
                    println("Clicked on feedback: ${it.id}")
                }
            )
        }
    }
}