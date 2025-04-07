package by.bsu.flightwise.data.dao.impl

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import by.bsu.flightwise.data.dao.UserDao
import by.bsu.flightwise.data.database.DatabaseHelper
import by.bsu.flightwise.data.entity.User
import java.util.Date

abstract class UserDaoImpl(private val db: SQLiteDatabase) : UserDao {
    override fun insert(user: User): Long {
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_USERNAME, user.username)
            put(DatabaseHelper.COLUMN_PASSWORD_HASH, user.passwordHash)
            put(DatabaseHelper.COLUMN_ROLE, user.role)
            put(DatabaseHelper.COLUMN_CREATED_AT, Date().time)
        }
        return db.insert(DatabaseHelper.TABLE_USERS, null, values)
    }

    override fun getById(id: Long): User? {
        val cursor = db.query(
            DatabaseHelper.TABLE_USERS,
            null,
            "${DatabaseHelper.COLUMN_ID} = ?",
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
            put(DatabaseHelper.COLUMN_USERNAME, user.username)
            put(DatabaseHelper.COLUMN_PASSWORD_HASH, user.passwordHash)
            put(DatabaseHelper.COLUMN_ROLE, user.role)
        }
        return db.update(
            DatabaseHelper.TABLE_USERS,
            values,
            "${DatabaseHelper.COLUMN_ID} = ?",
            arrayOf(user.id.toString())
        )
    }

    override fun delete(id: Long): Int {
        return db.delete(
            DatabaseHelper.TABLE_USERS,
            "${DatabaseHelper.COLUMN_ID} = ?",
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
            "${DatabaseHelper.COLUMN_CREATED_AT} DESC"
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
            "${DatabaseHelper.COLUMN_USERNAME} = ?",
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
                id = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)),
                username = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USERNAME)),
                passwordHash = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PASSWORD_HASH)),
                role = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ROLE)),
                createdAt = Date(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CREATED_AT)))
            )
        } catch (e: Exception) {
            null
        }
    }
}