package com.mityushovn.miningquiz.database

import android.content.Context
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.mityushovn.miningquiz.database.AppSQLiteContract.DATABASE_NAME
import java.io.File
import java.io.FileOutputStream

private const val DATABASE_VERSION = 1
private const val ASSETS_PATH = "/home/mityushovn/AndroidStudioProjects/MiningQuiz/app/src/main/assets"

class AppSQLiteHelper(private val applicationContext: Context) : SQLiteOpenHelper(
    applicationContext,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {
    private val preferences: SharedPreferences = applicationContext.getSharedPreferences(
        "${applicationContext.packageName}.database_versions",
        Context.MODE_PRIVATE
    )

    private fun installedDatabaseIsOutdated(): Boolean {
        return preferences.getInt(DATABASE_NAME, 0) < DATABASE_VERSION
    }

    private fun writeDatabaseVersionInPreferences() {
        preferences.edit().apply {
            putInt(DATABASE_NAME, DATABASE_VERSION)
            apply()
        }
    }

    private fun installDatabaseFromAssets() {
//        val inputStream = applicationContext.assets.open("$ASSETS_PATH/$DATABASE_NAME.sqlite3")
        val inputStream = applicationContext.assets.open("$DATABASE_NAME.sqlite3")

        try {
            val outputFile = File(applicationContext.getDatabasePath(DATABASE_NAME).path)
            val outputStream = FileOutputStream(outputFile)

            inputStream.copyTo(outputStream)
            inputStream.close()

            outputStream.flush()
            outputStream.close()
        } catch (exception: Throwable) {
            throw RuntimeException("The $DATABASE_NAME database couldn't be installed.", exception)
        }
    }

    @Synchronized
    private fun installOrUpdateIfNecessary() {
        if (installedDatabaseIsOutdated()) {
            applicationContext.deleteDatabase(DATABASE_NAME)
            installDatabaseFromAssets()
            writeDatabaseVersionInPreferences()
        }
    }

    override fun getWritableDatabase(): SQLiteDatabase {
        installOrUpdateIfNecessary()
        return super.getWritableDatabase()
//        throw RuntimeException("The $DATABASE_NAME database is not writable.")
    }

    override fun getReadableDatabase(): SQLiteDatabase {
        installOrUpdateIfNecessary()
        return super.getReadableDatabase()
    }

    /*

     */
    override fun onCreate(database: SQLiteDatabase) {
        // nothing to do
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        // nothing to do
    }
}