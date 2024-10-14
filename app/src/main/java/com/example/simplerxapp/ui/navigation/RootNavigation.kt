package com.example.simplerxapp.ui.navigation

import kotlinx.serialization.Serializable

sealed interface RootNavigation {
    @Serializable
    data object SubjectsDestination: RootNavigation
    @Serializable
    data class ChapterDestination(val subjectId: String): RootNavigation
}