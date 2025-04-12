package by.bsu.flightwise.data.dao.impl

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import by.bsu.flightwise.data.dao.FlightDao
import by.bsu.flightwise.data.database.DatabaseHelper
import by.bsu.flightwise.data.entity.Flight
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FlightDaoImpl(private val db: SQLiteDatabase) : FlightDao {
    companion object {
        private const val TABLE_FLIGHTS = DatabaseHelper.TABLE_FLIGHTS
        private const val COLUMN_ID = DatabaseHelper.COLUMN_ID
        private const val COLUMN_AIRPLANE_ID = DatabaseHelper.COLUMN_AIRPLANE_ID
        private const val COLUMN_DEPARTURE_AIRPORT_ID = DatabaseHelper.COLUMN_DEPARTURE_AIRPORT_ID
        private const val COLUMN_ARRIVAL_AIRPORT_ID = DatabaseHelper.COLUMN_ARRIVAL_AIRPORT_ID
        private const val COLUMN_DEPARTURE_TIME = DatabaseHelper.COLUMN_DEPARTURE_TIME
        private const val COLUMN_ARRIVAL_TIME = DatabaseHelper.COLUMN_ARRIVAL_TIME
        private const val COLUMN_PRICING_RULE_ID = DatabaseHelper.COLUMN_PRICING_RULE_ID

        private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    }

    override fun insert(flight: Flight): Long {
        val values = ContentValues().apply {
            put(COLUMN_AIRPLANE_ID, flight.airplaneId)
            put(COLUMN_DEPARTURE_AIRPORT_ID, flight.fromAirportId)
            put(COLUMN_ARRIVAL_AIRPORT_ID, flight.toAirportId)
            put(COLUMN_DEPARTURE_TIME, flight.departureTime.time)
            put(COLUMN_ARRIVAL_TIME, flight.arrivalTime.time)
            put(COLUMN_PRICING_RULE_ID, flight.pricingRuleId)
        }
        return db.insert(TABLE_FLIGHTS, null, values)
    }

    override fun getById(id: Long): Flight? {
        val cursor = db.query(
            TABLE_FLIGHTS,
            null,
            "$COLUMN_ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        return cursor.use {
            if (it.moveToFirst()) {
                cursorToFlight(it)
            } else {
                null
            }
        }
    }

    override fun update(flight: Flight): Int {
        val values = ContentValues().apply {
            put(COLUMN_AIRPLANE_ID, flight.airplaneId)
            put(COLUMN_DEPARTURE_AIRPORT_ID, flight.fromAirportId)
            put(COLUMN_ARRIVAL_AIRPORT_ID, flight.toAirportId)
            put(COLUMN_DEPARTURE_TIME, flight.departureTime.time)
            put(COLUMN_ARRIVAL_TIME, flight.arrivalTime.time)
            put(COLUMN_PRICING_RULE_ID, flight.pricingRuleId)
        }
        return db.update(
            TABLE_FLIGHTS,
            values,
            "$COLUMN_ID = ?",
            arrayOf(flight.id.toString())
        )
    }

    override fun delete(id: Long): Int {
        return db.delete(
            TABLE_FLIGHTS,
            "$COLUMN_ID = ?",
            arrayOf(id.toString())
        )
    }

    override fun getAll(): List<Flight> {
        val cursor = db.query(
            TABLE_FLIGHTS,
            null,
            null,
            null,
            null,
            null,
            "$COLUMN_DEPARTURE_TIME ASC"
        )
        return cursor.use {
            val flights = mutableListOf<Flight>()
            while (it.moveToNext()) {
                cursorToFlight(it)?.let { flight -> flights.add(flight) }
            }
            flights
        }
    }

    override fun findByCitiesAndDateRange(
        departureCity: String,
        arrivalCity: String,
        startDate: Date,
        endDate: Date
    ): List<Flight> {
        val flights = mutableListOf<Flight>()
        val query = """
        SELECT f.*
        FROM $TABLE_FLIGHTS f
        JOIN airports dep ON f.$COLUMN_DEPARTURE_AIRPORT_ID = dep.id
        JOIN airports arr ON f.$COLUMN_ARRIVAL_AIRPORT_ID = arr.id
        WHERE dep.city = ? AND arr.city = ? AND f.$COLUMN_DEPARTURE_TIME BETWEEN ? AND ?
        ORDER BY f.$COLUMN_DEPARTURE_TIME ASC
    """.trimIndent()

        val cursor = db.rawQuery(
            query,
            arrayOf(
                departureCity,
                arrivalCity,
                startDate.time.toString(),
                endDate.time.toString()
            )
        )

        cursor.use {
            while (it.moveToNext()) {
                cursorToFlight(it)?.let { flight -> flights.add(flight) }
            }
        }
        return flights
    }

    override fun findByCountriesAndDateRange(
        departureCountry: String,
        arrivalCountry: String,
        startDate: Date,
        endDate: Date
    ): List<Flight> {
        val flights = mutableListOf<Flight>()
        val query = """
        SELECT f.*
        FROM $TABLE_FLIGHTS f
        JOIN airports dep ON f.$COLUMN_DEPARTURE_AIRPORT_ID = dep.id
        JOIN airports arr ON f.$COLUMN_ARRIVAL_AIRPORT_ID = arr.id
        WHERE dep.country = ? AND arr.country = ? AND f.$COLUMN_DEPARTURE_TIME BETWEEN ? AND ?
        ORDER BY f.$COLUMN_DEPARTURE_TIME ASC
    """.trimIndent()

        val cursor = db.rawQuery(
            query,
            arrayOf(
                departureCountry,
                arrivalCountry,
                startDate.time.toString(),
                endDate.time.toString()
            )
        )

        cursor.use {
            while (it.moveToNext()) {
                cursorToFlight(it)?.let { flight -> flights.add(flight) }
            }
        }
        return flights
    }


    private fun cursorToFlight(cursor: Cursor): Flight? {
        return try {
            Flight(
                id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                airplaneId = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_AIRPLANE_ID)),
                fromAirportId = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_DEPARTURE_AIRPORT_ID)),
                toAirportId = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ARRIVAL_AIRPORT_ID)),
                departureTime = dateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DEPARTURE_TIME)))!!,
                arrivalTime = dateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ARRIVAL_TIME)))!!,
                pricingRuleId = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_PRICING_RULE_ID))
            )
        } catch (e: Exception) {
            null
        }
    }
} 