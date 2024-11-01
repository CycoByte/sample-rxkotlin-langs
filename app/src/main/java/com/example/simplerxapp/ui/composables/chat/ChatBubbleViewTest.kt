package com.example.simplerxapp.ui.composables.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.simplerxapp.ui.chat.models.SentMessageStatus

enum class BubbleArrowType {
    NO_ARROW,
    SENDER,
    RECEIVER
}

@Composable
fun ChatBubbleViewTest(
    modifier: Modifier,
    text: String,
    color: Color,
    type: BubbleArrowType,
    timeStamp: String,
    status: SentMessageStatus,
    optionalInfo: @Composable () -> Unit
) {

    val statusAlignment = remember(type) {
        when (type) {
            BubbleArrowType.SENDER -> Alignment.End
            else -> Alignment.Start
        }
    }

    Column(
        modifier = Modifier
    ) {
        Surface(
            modifier = Modifier,
            shape = RoundedCornerShape(
                topStart = 48f,
                topEnd = 48f,
                bottomStart = if (true) 48f else 0f,
                bottomEnd = if (true) 0f else 48f
            ),
            color = color
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Start),
                    text = text
                )

                Row(
                    modifier = Modifier
                        .align(statusAlignment)
                ) {
                    Text(
                        text = timeStamp,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.DarkGray
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun previewChat() {
    Box(
        modifier = Modifier.padding(16.dp)
    ) {
        ChatBubbleViewTest(
            modifier = Modifier.padding(16.dp),
            text = "test chat bubble skjdhfskjdfh skjdhf sfjkh skjdfh sjkdfhs fkjh ksjhdf ksjhdf",
            color = Color(0xffF3A3A3),
            type = BubbleArrowType.SENDER,
            timeStamp = "12:00",
            status = SentMessageStatus.SENDING
        ) {

        }
    }
}