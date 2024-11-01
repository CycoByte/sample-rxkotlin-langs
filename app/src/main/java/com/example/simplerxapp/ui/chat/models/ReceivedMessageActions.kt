package com.example.simplerxapp.ui.chat.models

import com.example.simplerxapp.R

enum class ReceivedMessageActions {
    LISTEN,
    COPY,
    MORE_INFO,
    TRANSLATION;

    companion object {
        fun ReceivedMessageActions.getImageResource(): Int {
            return when (this) {
                LISTEN -> R.drawable.ic_volume_audio
                COPY -> R.drawable.baseline_content_copy_24
                MORE_INFO -> R.drawable.baseline_info_outline_24
                TRANSLATION -> R.drawable.ic_translation
            }
        }
    }
}