package com.example.simplerxapp.managers

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.simplerxapp.database.ApplicationDatabase
import com.example.simplerxapp.database.ApplicationDatabase.Companion.DATABASE_NAME
import com.example.simplerxapp.database.ApplicationDatabase.Companion.instance
import com.example.simplerxapp.models.FileDetails
import java.io.File
import java.io.IOException
import java.lang.ref.WeakReference

class AppDatabaseManager(
    context: Context
): DatabaseManager {

    private val context: WeakReference<Context> = WeakReference(context)

    override fun createDatabase(): Boolean {
        context.get()?.let {
            ApplicationDatabase.create(it)
            return true
        }
        return false
    }

    override fun deleteDatabase(): Boolean {
        return false
    }

    override fun backupDatabase(): Boolean {
        context.get()?.let { ctx ->
            return try {
                val dbFile = ctx.getDatabasePath(DATABASE_NAME) // Replace with your database name
                val backupFile = File(ctx.filesDir, "backup-$DATABASE_NAME")
                dbFile.copyTo(backupFile, overwrite = true) // Copy database to backup location
                Log.d("Database", "Database file attributes:\n${FileDetails.ofFile(dbFile)}")
                Log.d("Database", "Backup file attributes:\n${FileDetails.ofFile(backupFile)}")
                true
            } catch (e: IOException) {
                e.printStackTrace()
                false
            }
            return true
        }
        return false
    }

    override fun restoreDatabase(): Boolean {
        context.get()?.let { ctx ->
            return try {
                instance.close()
                val dbFile = ctx.getDatabasePath(DATABASE_NAME)
                val backupFile = File(ctx.filesDir, "backup-$DATABASE_NAME")

                if (backupFile.exists()) {
                    backupFile.copyTo(dbFile, overwrite = true) // Replace the database file

                    Log.d("Database", "Database file attributes:\n${FileDetails.ofFile(dbFile)}")
                    Log.d("Database", "Backup file attributes:\n${FileDetails.ofFile(backupFile)}")
                    true
                } else {
                    false // Backup file does not exist
                }
            } catch (e: IOException) {
                e.printStackTrace()
                false
            } finally {
                val packageManager = ctx.packageManager
                val intent = packageManager.getLaunchIntentForPackage(ctx.packageName)
                if (intent != null) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    ctx.startActivity(intent)
                    android.os.Process.killProcess(android.os.Process.myPid())
                }
            }
        }
        return false
    }
}