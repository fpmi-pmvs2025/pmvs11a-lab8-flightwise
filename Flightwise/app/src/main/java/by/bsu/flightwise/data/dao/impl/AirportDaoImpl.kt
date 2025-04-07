package by.bsu.flightwise.data.dao.impl

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import by.bsu.flightwise.data.dao.AirportDao
import by.bsu.flightwise.data.entity.Airport

class AirportDaoImpl(private val db: SQLiteDatabase) : AirportDao {
    companion object {
        private const val TABLE_AIRPORTS = "airports"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_CODE = "code"
        private const val COLUMN_CITY = "city"
        private const val COLUMN_COUNTRY = "country"
    }

    override fun insert(airport: Airport): Long {
        val values = ContentValues().apply {
            put(COLUMN_NAME, airport.name)
            put(COLUMN_CODE, airport.code.uppercase())
            put(COLUMN_CITY, airport.city)
            put(COLUMN_COUNTRY, airport.country)
        }
        return db.insert(TABLE_AIRPORTS, null, values)
    }

    override fun getById(id: Long): Airport? {
        val cursor = db.query(
            TABLE_AIRPORTS,
            null,
            "$COLUMN_ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        return cursor.use {
            if (it.moveToFirst()) {
                cursorToAirport(it)
            } else {
                null
            }
        }
    }

    override fun update(airport: Airport): Int {
        val values = ContentValues().apply {
            put(COLUMN_NAME, airport.name)
            put(COLUMN_CODE, airport.code.uppercase())
            put(COLUMN_CITY, airport.city)
            put(COLUMN_COUNTRY, airport.country)
        }
        return db.update(
            TABLE_AIRPORTS,
            values,
            "$COLUMN_ID = ?",
            arrayOf(airport.id.toString())
        )
    }

    override fun delete(id: Long): Int {
        return db.delete(
            TABLE_AIRPORTS,
            "$COLUMN_ID = ?",
            arrayOf(id.toString())
        )
    }

    override fun getAll(): List<Airport> {
        val airports = mutableListOf<Airport>()
        val cursor = db.query(
            TABLE_AIRPORTS,
            null,
            null,
            null,
            null,
            null,
            "$COLUMN_NAME ASC"
        )
        cursor.use {
            while (it.moveToNext()) {
                airports.add(cursorToAirport(it))
            }
        }
        return airports
    }

    override fun findByCode(code: String): Airport? {
        val cursor = db.query(
            TABLE_AIRPORTS,
            null,
            "$COLUMN_CODE = ?",
            arrayOf(code.uppercase()),
            null,
            null,
            null
        )
        return cursor.use {
            if (it.moveToFirst()) {
                cursorToAirport(it)
            } else {
                null
            }
        }
    }

    override fun findByCity(city: String): List<Airport> {
        val airports = mutableListOf<Airport>()
        val cursor = db.query(
            TABLE_AIRPORTS,
            null,
            "$COLUMN_CITY = ?",
            arrayOf(city),
            null,
            null,
            "$COLUMN_NAME ASC"
        )
        cursor.use {
            while (it.moveToNext()) {
                airports.add(cursorToAirport(it))
            }
        }
        return airports
    }

    override fun findByCountry(country: String): List<Airport> {
        val airports = mutableListOf<Airport>()
        val cursor = db.query(
            TABLE_AIRPORTS,
            null,
            "$COLUMN_COUNTRY = ?",
            arrayOf(country),
            null,
            null,
            "$COLUMN_NAME ASC"
        )
        cursor.use {
            while (it.moveToNext()) {
                airports.add(cursorToAirport(it))
            }
        }
        return airports
    }

    override fun searchByName(query: String): List<Airport> {
        val airports = mutableListOf<Airport>()
        val cursor = db.query(
            TABLE_AIRPORTS,
            null,
            "$COLUMN_NAME LIKE ?",
            arrayOf("%$query%"),
            null,
            null,
            "$COLUMN_NAME ASC"
        )
        cursor.use {
            while (it.moveToNext()) {
                airports.add(cursorToAirport(it))
            }
        }
        return airports
    }

    private fun cursorToAirport(cursor: Cursor): Airport {
        return Airport(
            id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)),
            name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
            code = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CODE)),
            city = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CITY)),
            country = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COUNTRY))
        )
    }
} 