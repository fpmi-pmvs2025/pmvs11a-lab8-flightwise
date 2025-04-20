package by.bsu.flightwise.service

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import java.io.FileOutputStream

@SuppressLint("Range")
fun setupDatabase(context: Context) {
    val dbName = "flightwise.db"
    val dbFile = context.getDatabasePath(dbName)

    if (!dbFile.exists()) {
        try {
            dbFile.parentFile?.mkdirs()
            context.assets.open(dbName).use { inputStream ->
                FileOutputStream(dbFile).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
        } catch (e: Exception) {
            null
        }
    } else {
        null
    }
}