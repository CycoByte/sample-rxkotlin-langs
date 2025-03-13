package com.example.simplerxapp.managers

interface DatabaseManager {

    fun createDatabase(): Boolean

    fun deleteDatabase(): Boolean

    fun backupDatabase(): Boolean

    fun restoreDatabase(): Boolean
}