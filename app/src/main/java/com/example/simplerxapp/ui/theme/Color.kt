package com.example.simplerxapp.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val CL_Primary500 = Color(0xFF357CA2)
val Cl_Success300 = Color(0xFF38cd82)
val Cl_Success500 = Color(0xFF228B22)
val Cl_Warning500 = Color(0xffEFBF1F)
val CL_Error300 = Color(0xFFFF7f7f)
val Cl_Error500 = Color(0xFF8b0000)
val CL_MediumGray = Color(0xFF3C3C3C)

val CL_SentBubbleColor = Color(0xFF357CA2)
val CL_ReceivedBubbleColor = Color(0xFFF5F5F5)

val CL_ChatInputFocusedContainerColor = Color(0xFFe7f0f9)
val CL_ChatInputUnfocusedContainerColor = Color(0xFFF6F5F2)


val ColorScheme.sentBubbleColor: Color
    get() = CL_SentBubbleColor

val ColorScheme.receivedBubbleColor: Color
    get() = CL_ReceivedBubbleColor

val ColorScheme.primaryButtonFillColor: Color
    get() = CL_Primary500

val ColorScheme.inputBorderColor: Color
    get() = CL_Primary500

val ColorScheme.neutralMediumColor: Color
    get() = CL_MediumGray
