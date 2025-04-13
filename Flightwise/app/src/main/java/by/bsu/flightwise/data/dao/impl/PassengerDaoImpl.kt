package by.bsu.flightwise.data.dao.impl

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import by.bsu.flightwise.data.dao.PassengerDao
import by.bsu.flightwise.data.database.DatabaseHelper
import by.bsu.flightwise.data.entity.Passenger
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PassengerDaoImpl(private val db: SQLiteDatabase) : PassengerDao {
    companion object {
        private const val TABLE_PASSENGERS = DatabaseHelper.TABLE_PASSENGERS
        private const val COLUMN_ID = DatabaseHelper.COLUMN_ID
        private const val COLUMN_NAME = DatabaseHelper.COLUMN_NAME
        private const val COLUMN_SURNAME = DatabaseHelper.COLUMN_SURNAME
        private const val COLUMN_PASSPORT_NUMBER = DatabaseHelper.COLUMN_PASSPORT_NUMBER
        private const val COLUMN_BIRTH_DATE = DatabaseHelper.COLUMN_BIRTH_DATE
        
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    }

    override fun insert(passenger: Passenger): Long {
        val values = ContentValues().apply {
            put(COLUMN_NAME, passenger.name)
            put(COLUMN_SURNAME, passenger.surname)
            put(COLUMN_PASSPORT_NUMBER, passenger.passportNumber)
            put(COLUMN_BIRTH_DATE, dateFormat.format(passenger.birthDate))
        }
        return db.insert(TABLE_PASSENGERS, null, values)
    }

    override fun getById(id: Long): Passenger? {
        val cursor = db.query(
            TABLE_PASSENGERS,
            null,
            "$COLUMN_ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        return cursor.use {
            if (it.moveToFirst()) {
                cursorToPassenger(it)
            } else {
                null
            }
        }
    }

    override fun update(passenger: Passenger): Int {
        val values = ContentValues().apply {
            put(COLUMN_NAME, passenger.name)
            put(COLUMN_SURNAME, passenger.surname)
            put(COLUMN_PASSPORT_NUMBER, passenger.passportNumber)
            put(COLUMN_BIRTH_DATE, dateFormat.format(passenger.birthDate))
        }
        return db.update(
            TABLE_PASSENGERS,
            values,
            "$COLUMN_ID = ?",
            arrayOf(passenger.id.toString())
        )
    }

    override fun delete(id: Long): Int {
        return db.delete(
            TABLE_PASSENGERS,
            "$COLUMN_ID = ?",
            arrayOf(id.toString())
        )
    }

    override fun getAll(): List<Passenger> {
        val passengers = mutableListOf<Passenger>()
        val cursor = db.query(
            TABLE_PASSENGERS,
            null,
            null,
            null,
            null,
            null,
            "$COLUMN_SURNAME ASC, $COLUMN_NAME ASC"
        )
        cursor.use {
            while (it.moveToNext()) {
                passengers.add(cursorToPassenger(it))
            }
        }
        return passengers
    }

    private fun cursorToPassenger(cursor: Cursor): Passenger {
        return Passenger(
            id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)),
            name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
            surname = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SURNAME)),
            passportNumber = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSPORT_NUMBER)),
            birthDate = dateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BIRTH_DATE)))!!
        )
    }
} 