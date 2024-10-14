package com.example.simplerxapp

import android.app.Application
import com.example.simplerxapp.database.ApplicationDatabase

class SampleApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        ApplicationDatabase.create(this.baseContext)
    }
}