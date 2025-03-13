package com.example.simplerxapp

import android.app.Application
import com.example.simplerxapp.managers.AppDatabaseManager
import com.example.simplerxapp.managers.DatabaseManager

class SampleApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        databaseManager = AppDatabaseManager(this.baseContext).also {
            it.createDatabase()
        }
    }

    companion object {
        lateinit var databaseManager: DatabaseManager
    }
}