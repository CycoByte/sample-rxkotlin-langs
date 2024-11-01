package com.example.simplerxapp.ui.composables.chat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simplerxapp.R
import com.example.simplerxapp.ui.theme.CL_ChatInputFocusedContainerColor
import com.example.simplerxapp.ui.theme.CL_ChatInputUnfocusedContainerColor
import com.example.simplerxapp.ui.theme.inputBorderColor
import com.example.simplerxapp.ui.theme.neutralMediumColor
import com.example.simplerxapp.ui.theme.primaryButtonFillColor

@Composable
fun ChatInputView(
    modifier: Modifier,
    typed: String,
    hint: String,
    onTextInput: (String) -> Unit,
    onSendAction: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier.weight(0.9f),
            value = typed,
            placeholder = {
                Text(text = hint)
            },
            onValueChange = onTextInput,
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.inputBorderColor,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.neutralMediumColor,
                cursorColor = MaterialTheme.colorScheme.inputBorderColor,
                focusedPlaceholderColor = MaterialTheme.colorScheme.neutralMediumColor,
                focusedContainerColor = CL_ChatInputFocusedContainerColor,
                unfocusedContainerColor = CL_ChatInputUnfocusedContainerColor
            )
        )

        Surface(
            modifier = Modifier
                .defaultMinSize(52.dp, 46.dp)
                .padding(start = 6.dp),
            onClick = onSendAction,
            shape = RoundedCornerShape(8.dp),
            enabled = typed.isNotEmpty(),
            color = MaterialTheme.colorScheme.primaryButtonFillColor
        ) {
            Icon(
                modifier = Modifier
                    .padding(10.dp)
                    .size(18.dp, 20.dp),
                painter = painterResource(R.drawable.outline_send_24),
                contentDescription = "send",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InputPreview() {

    var text by remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier
            .padding(16.dp)
    ) {
        ChatInputView(
            modifier = Modifier
                .fillMaxWidth(),
            typed = text,
            onTextInput = {
                text = it
            },
            hint = "write a message",
            onSendAction = {
                println("Send message")
            }
        )
    }
}