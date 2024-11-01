package com.example.simplerxapp.tools

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Utils {

    @RequiresApi(Build.VERSION_CODES.O)
    fun LocalDateTime.getTimeNow(): String {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return this.format(formatter)
    }
}