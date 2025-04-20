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
            Log.d("DB_CHECK", "Database copied successfully to: ${dbFile.path}")
        } catch (e: Exception) {
            Log.e("DB_CHECK", "Error copying database: ${e.message}", e)
        }
    } else {
        Log.d("DB_CHECK", "Database already exists at: ${dbFile.path}")
    }

    try {
        val db = SQLiteDatabase.openDatabase(dbFile.path, null, SQLiteDatabase.OPEN_READONLY)
        val tableCursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null)
        if (tableCursor.moveToFirst()) {
            do {
                val tableName = tableCursor.getString(0)
                Log.d("DB_CHECK", "======================================")
                Log.d("DB_CHECK", "Table: $tableName")

                val columnsCursor = db.rawQuery("PRAGMA table_info($tableName)", null)
                val columns = mutableListOf<String>()
                if (columnsCursor.moveToFirst()) {
                    do {
                        val colName = columnsCursor.getString(columnsCursor.getColumnIndex("name"))
                        columns.add(colName)
                    } while (columnsCursor.moveToNext())
                }
                columnsCursor.close()
                Log.d("DB_CHECK", "Columns: ${columns.joinToString(", ")}")

                val contentCursor = db.rawQuery("SELECT * FROM $tableName", null)
                if (contentCursor.moveToFirst()) {
                    do {
                        val rowValues = columns.map { col ->
                            try {
                                contentCursor.getString(contentCursor.getColumnIndex(col)) ?: "null"
                            } catch (ex: Exception) {
                                "error"
                            }
                        }
                        Log.d("DB_CHECK", "Row: ${rowValues.joinToString(", ")}")
                    } while (contentCursor.moveToNext())
                } else {
                    Log.d("DB_CHECK", "Table $tableName is empty")
                }
                contentCursor.close()
            } while (tableCursor.moveToNext())
        } else {
            Log.d("DB_CHECK", "No tables found in the database")
        }
        tableCursor.close()
        db.close()
    } catch (e: Exception) {
        Log.e("DB_CHECK", "Error reading database content: ${e.message}", e)
    }
}