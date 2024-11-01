package com.example.simplerxapp.ui.chat

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simplerxapp.ui.UIState
import com.example.simplerxapp.ui.chat.models.ChatMessage
import com.example.simplerxapp.ui.composables.chat.ChatInputView
import com.example.simplerxapp.ui.composables.chat.ReceiverBubbleView
import com.example.simplerxapp.ui.composables.chat.SenderBubbleView
import com.example.simplerxapp.ui.composables.containers.MainContentHolder
import com.example.simplerxapp.ui.theme.primaryButtonFillColor

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatWithDolphinView(
    modifier: Modifier,
    onBack: () -> Unit,
) {
    val listState = rememberLazyListState()
    val focus = LocalFocusManager.current
    val viewModel: ChatWithDolphinViewModel = viewModel()
    val messagesList = remember {
        viewModel.messagesListState
    }

    val lastSentId by remember {
        derivedStateOf {
            messagesList.filterIsInstance<ChatMessage.Sent>().lastOrNull()?.id
        }
    }

    val lastReceivedId by remember {
        derivedStateOf {
            messagesList.filterIsInstance<ChatMessage.Received>().lastOrNull()?.id
        }
    }

    var typedMessage by remember {
        mutableStateOf("")
    }

    LaunchedEffect(viewModel) {
        viewModel.loadMessages()
    }

    LaunchedEffect(messagesList.size) {
        Log.d("TAG", "Last sent id updated: $lastSentId")
        if (messagesList.isNotEmpty()) {
            listState.animateScrollToItem(messagesList.lastIndex)
        }
    }

    MainContentHolder(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "talk to the dolphin",
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryButtonFillColor,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        uiState = UIState.Idle
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
                state = listState
            ) {
                items(messagesList) { message ->
                    if (message is ChatMessage.Sent) {
                        SenderBubbleView(
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .fillMaxWidth(),
                            message = message,
                            isLast = lastSentId == message.id,
                            onViewFeedbackAction = {
                                Log.d("TEST", "Handle this $it")
                            }
                        )
                    } else if (message is ChatMessage.Received) {
                        ReceiverBubbleView(
                            modifier = Modifier.fillMaxWidth(),
                            message = message,
                            isLast = lastReceivedId == message.id,
                            contentPadding = PaddingValues(start = 16.dp),
                            onAction = {
                                Log.d("TEST", "Handle this $it")
                            },
                            onSuggestionClicked = {
                                Log.d("TEST", "Handle this $it")
                            }
                        )
                    }
                }
                item {
                    Spacer(Modifier.size(2.dp))
                }
            }

            ChatInputView(
                modifier = Modifier
                    .heightIn(min = 38.dp)
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .fillMaxWidth(),
                typed = typedMessage,
                hint = "write a message",
                onTextInput = {
                    typedMessage = it
                },
                onSendAction = {
                    viewModel.sendMessage(typedMessage)
                    typedMessage = ""
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun ChatPreview() {
    ChatWithDolphinView(
        modifier = Modifier.fillMaxSize(),
        onBack = {

        }
    )
}