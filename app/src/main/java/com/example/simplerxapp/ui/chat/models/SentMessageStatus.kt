package com.example.simplerxapp.ui.chat.models

import com.example.simplerxapp.R

enum class SentMessageStatus {
    NONE,
    SENDING,
    SENT,
    FAILED,
    SENT_RECEIVED;

    companion object {
        fun SentMessageStatus.getImageResource(): Int {
            return when (this) {
                NONE,
                SENDING -> R.drawable.baseline_access_time_24
                SENT_RECEIVED,
                SENT -> R.drawable.baseline_check_circle_outline_24
                FAILED -> R.drawable.baseline_error_outline_24
            }
        }
    }
}