package by.bsu.flightwise.data.dao.impl

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import by.bsu.flightwise.data.dao.UserDao
import by.bsu.flightwise.data.database.DatabaseHelper
import by.bsu.flightwise.data.entity.User
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

abstract class UserDaoImpl(private val db: SQLiteDatabase) : UserDao {
    companion object {
        private const val TABLE_USERS = DatabaseHelper.TABLE_USERS
        private const val COLUMN_ID = DatabaseHelper.COLUMN_ID
        private const val COLUMN_USERNAME = DatabaseHelper.COLUMN_USERNAME
        private const val COLUMN_PASSWORD_HASH = DatabaseHelper.COLUMN_PASSWORD_HASH
        private const val COLUMN_CREATED_AT = DatabaseHelper.COLUMN_CREATED_AT

        private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    }

    override fun insert(user: User): Long {
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, user.username)
            put(COLUMN_PASSWORD_HASH, user.passwordHash)
            put(COLUMN_CREATED_AT, Date().time)
        }
        return db.insert(TABLE_USERS, null, values)
    }

    override fun getById(id: Long): User? {
        val cursor = db.query(
            TABLE_USERS,
            null,
            "$COLUMN_ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        return cursor.use {
            if (it.moveToFirst()) {
                cursorToUser(it)
            } else {
                null
            }
        }
    }

    override fun update(user: User): Int {
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, user.username)
            put(COLUMN_PASSWORD_HASH, user.passwordHash)
        }
        return db.update(
            TABLE_USERS,
            values,
            "$COLUMN_ID = ?",
            arrayOf(user.id.toString())
        )
    }

    override fun delete(id: Long): Int {
        return db.delete(
            DatabaseHelper.TABLE_USERS,
            "$COLUMN_ID = ?",
            arrayOf(id.toString())
        )
    }

    override fun getAll(): List<User> {
        val cursor = db.query(
            DatabaseHelper.TABLE_USERS,
            null,
            null,
            null,
            null,
            null,
            "$COLUMN_CREATED_AT DESC"
        )
        return cursor.use {
            val users = mutableListOf<User>()
            while (it.moveToNext()) {
                cursorToUser(it)?.let { user -> users.add(user) }
            }
            users
        }
    }

    override fun findByUsername(username: String): User? {
        val cursor = db.query(
            DatabaseHelper.TABLE_USERS,
            null,
            "$COLUMN_USERNAME = ?",
            arrayOf(username),
            null,
            null,
            null
        )
        return cursor.use {
            if (it.moveToFirst()) {
                cursorToUser(it)
            } else {
                null
            }
        }
    }

    private fun cursorToUser(cursor: Cursor): User? {
        return try {
            User(
                id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME)),
                passwordHash = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PASSWORD_HASH)),
                createdAt = dateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CREATED_AT)))!!
            )
        } catch (e: Exception) {
            null
        }
    }
}