package com.example.simplerxapp.ui

import android.content.Context

sealed interface ActionIntent {
    data class CreateDatabaseBackup(val context: Context): ActionIntent
    data class RestoreDatabaseBackup(val context: Context): ActionIntent
    data object ReloadData: ActionIntent
}