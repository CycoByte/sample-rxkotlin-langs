package com.example.simplerxapp.ui.chat

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplerxapp.tools.Utils.getTimeNow
import com.example.simplerxapp.ui.chat.models.ChatMessage
import com.example.simplerxapp.ui.chat.models.ReceivedMessageActions
import com.example.simplerxapp.ui.chat.models.SentMessageStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.O)
class ChatWithDolphinViewModel: ViewModel() {

    val messagesListState = mutableStateListOf<ChatMessage>()

    val randomSentSuccessFeedback = listOf(
        ChatMessage.Feedback.Positive("id",),
        ChatMessage.Feedback.Suggestion("id", "you can do better", "this is a very large test to see how this will fit in the view with multiple lines, here the used is writing way too much.",
        ),
        ChatMessage.Feedback.Wrong("id", "invalid input", "this is a very large test to see how this will fit in the view with multiple lines, here the used is writing way too much.",
        )
    )

    fun loadMessages() {
        if (messagesListState.isEmpty()) {
            viewModelScope.launch {
                delay(2000)
                messagesListState.addAll(mockMessages.sortedBy { it.order })
            }
        }
    }

    fun sendMessage(message: String) {
        val lastOrder = messagesListState.lastOrNull()?.order ?: 1
        val constructedMessage = ChatMessage.Sent(
            id = UUID.randomUUID().toString(),
            order = lastOrder + 1,
            body = message,
            time = LocalDateTime.now().getTimeNow(),
            status = SentMessageStatus.SENDING,
            feedback = null
        )
        messagesListState.add(constructedMessage)
        if (Random.nextBoolean()) {
            sendSuccessMockFlow(constructedMessage.id)
        } else {
            sendFailedMockFlow(constructedMessage.id)
        }
    }

    private fun sendSuccessMockFlow(id: String) {
        viewModelScope.launch {
            delay(1200)
            updateSentMessageStatus(SentMessageStatus.SENT)
            delay(800)
            updateSentMessageStatus(SentMessageStatus.SENT_RECEIVED)
            launch {
                getDolphinRandomResponse()
            }
        }
    }

    private suspend fun getDolphinRandomResponse() {
        delay(900)
        val lastOrder = messagesListState.lastOrNull()?.order ?: 1
        val constructedMessage = ChatMessage.Received(
            id = UUID.randomUUID().toString(),
            order = lastOrder + 1,
            body = "Hello friend",
            time = LocalDateTime.now().getTimeNow(),
            actions = listOf(
                ReceivedMessageActions.LISTEN,
                ReceivedMessageActions.TRANSLATION
            ),
            suggestions = listOf(
                ChatMessage.Feedback.Suggestion(
                    id = "1",
                    shortMessage = "Rainbows"
                ),
                ChatMessage.Feedback.Suggestion(
                    id = "2",
                    shortMessage = "Unicorns"
                )
            )
        )
        messagesListState.add(constructedMessage)
    }

    private fun sendFailedMockFlow(id: String) {
        viewModelScope.launch {
            delay(1200)
            updateSentMessageStatus(SentMessageStatus.FAILED)
        }
    }


    private fun updateSentMessageStatus(status: SentMessageStatus) {
        messagesListState.filterIsInstance<ChatMessage.Sent>().lastOrNull()?.let {
            updateSentMessageStatus(it.id, status)
        }
    }

    private fun updateSentMessageStatus(id: String, status: SentMessageStatus) {
        val messageIndex = messagesListState.indexOfFirst { it.id == id }
        if (messageIndex >= 0) {
            val message = messagesListState[messageIndex] as ChatMessage.Sent
            if (status == SentMessageStatus.SENT_RECEIVED) {
                messagesListState[messageIndex] = message.copy(
                    status = status,
                    feedback = randomSentSuccessFeedback.random()
                )
            } else {
                messagesListState[messageIndex] = message.copy(status = status)
            }
        }
    }

    companion object {
        val mockMessages = listOf(
            ChatMessage.Received(
                id = UUID.randomUUID().toString(),
                order = 1,
                body = "an error!",
                time = "12:00",
            ),
            ChatMessage.Received(
                id = UUID.randomUUID().toString(),
                order = 2,
                body = "this is a test",
                time = "12:00",
                actions = listOf(
                    ReceivedMessageActions.LISTEN,
                    ReceivedMessageActions.COPY,
                    ReceivedMessageActions.MORE_INFO
                )
            ),
            ChatMessage.Received(
                id = UUID.randomUUID().toString(),
                order = 3,
                body = "this is a very large test to see how this will fit in the view with multiple lines, here the used is writing way too much.",
                time = "12:00",
                actions = listOf(
                    ReceivedMessageActions.LISTEN,
                    ReceivedMessageActions.TRANSLATION
                ),
                suggestions = listOf(
                    ChatMessage.Feedback.Suggestion(
                        id = "1",
                        shortMessage = "Rainbows"
                    ),
                    ChatMessage.Feedback.Suggestion(
                        id = "2",
                        shortMessage = "Unicorns"
                    )
                )
            ),
            ChatMessage.Sent(
                id = UUID.randomUUID().toString(),
                order = 4,
                body = "an error!",
                time = "12:00",
                status = SentMessageStatus.FAILED,
                feedback = null
            ),
            ChatMessage.Sent(
                id = UUID.randomUUID().toString(),
                order = 6,
                body = "this is a test",
                time = "12:00",
                status = SentMessageStatus.SENT_RECEIVED,
                feedback = ChatMessage.Feedback.Positive("fid1")
            ),
            ChatMessage.Sent(
                id = UUID.randomUUID().toString(),
                order = 8,
                body = "this is a test",
                time = "12:05",
                status = SentMessageStatus.SENT,
                feedback = ChatMessage.Feedback.Suggestion(
                    "fid2",
                    "grammar error \uD83E\uDD14"
                )
            ),
            ChatMessage.Sent(
                id = UUID.randomUUID().toString(),
                order = 9,
                body = "this is a very large test to see how this will fit in the view with multiple lines, here the used is writing way too much.",
                time = "12:05",
                status = SentMessageStatus.SENDING,
                feedback = ChatMessage.Feedback.Wrong("fid3", "invalid characters \uD83D\uDCA9")
            ),
            ChatMessage.Sent(
                id = UUID.randomUUID().toString(),
                order = 10,
                body = "thsending",
                time = "12:05",
                status = SentMessageStatus.SENDING,
                feedback = ChatMessage.Feedback.Wrong("fid3", "invalid characters")
            )
        )
    }
}