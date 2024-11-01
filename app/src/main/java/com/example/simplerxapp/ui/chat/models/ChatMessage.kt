package com.example.simplerxapp.ui.chat.models

sealed interface ChatMessage {

    val id: String
    val order: Int
    val body: String
    val time: String

    data class Sent(
        override val id: String,
        override val order: Int,
        override val body: String,
        override val time: String,
        val status: SentMessageStatus,
        val feedback: Feedback?
    ): ChatMessage

    data class Received(
        override val id: String,
        override val order: Int,
        override val body: String,
        override val time: String,
        val actions: List<ReceivedMessageActions> = listOf(),
        val suggestions: List<Feedback.Suggestion> = listOf()
    ): ChatMessage

    sealed interface Feedback {

        val id: String
        val shortMessage: String
        val fullMessage: String

        data class Positive(
            override val id: String,
            override val shortMessage: String = "",
            override val fullMessage: String = ""
        ): Feedback

        data class Suggestion(
            override val id: String,
            override val shortMessage: String,
            override val fullMessage: String = ""
        ): Feedback

        data class Wrong(
            override val id: String,
            override val shortMessage: String,
            override val fullMessage: String = ""
        ): Feedback
    }
}