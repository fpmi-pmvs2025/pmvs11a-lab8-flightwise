package by.bsu.flightwise.data.dao.impl

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import by.bsu.flightwise.data.dao.AirplaneDao
import by.bsu.flightwise.data.entity.Airplane
import java.util.Date

class AirplaneDaoImpl(private val db: SQLiteDatabase) : AirplaneDao {
    companion object {
        private const val TABLE_AIRPLANES = "airplanes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_MODEL = "model"
        private const val COLUMN_CAPACITY = "capacity"
    }

    override fun insert(airplane: Airplane): Long {
        val values = ContentValues().apply {
            put(COLUMN_MODEL, airplane.model)
            put(COLUMN_CAPACITY, airplane.capacity)
        }
        return db.insert(TABLE_AIRPLANES, null, values)
    }

    override fun getById(id: Long): Airplane? {
        val cursor = db.query(
            TABLE_AIRPLANES,
            null,
            "$COLUMN_ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        return cursor.use {
            if (it.moveToFirst()) {
                cursorToAirplane(it)
            } else {
                null
            }
        }
    }

    override fun update(airplane: Airplane): Int {
        val values = ContentValues().apply {
            put(COLUMN_MODEL, airplane.model)
            put(COLUMN_CAPACITY, airplane.capacity)
        }
        return db.update(
            TABLE_AIRPLANES,
            values,
            "$COLUMN_ID = ?",
            arrayOf(airplane.id.toString())
        )
    }

    override fun delete(id: Long): Int {
        return db.delete(
            TABLE_AIRPLANES,
            "$COLUMN_ID = ?",
            arrayOf(id.toString())
        )
    }

    override fun getAll(): List<Airplane> {
        val airplanes = mutableListOf<Airplane>()
        val cursor = db.query(
            TABLE_AIRPLANES,
            null,
            null,
            null,
            null,
            null,
            "$COLUMN_MODEL ASC"
        )
        cursor.use {
            while (it.moveToNext()) {
                airplanes.add(cursorToAirplane(it))
            }
        }
        return airplanes
    }

    override fun findByCapacityRange(minCapacity: Int, maxCapacity: Int): List<Airplane> {
        val airplanes = mutableListOf<Airplane>()
        val cursor = db.query(
            TABLE_AIRPLANES,
            null,
            "$COLUMN_CAPACITY BETWEEN ? AND ?",
            arrayOf(minCapacity.toString(), maxCapacity.toString()),
            null,
            null,
            "$COLUMN_CAPACITY ASC"
        )
        cursor.use {
            while (it.moveToNext()) {
                airplanes.add(cursorToAirplane(it))
            }
        }
        return airplanes
    }

    override fun findAvailableForFlight(departureTime: Date, arrivalTime: Date): List<Airplane> {
        val query = """
            SELECT DISTINCT a.* FROM $TABLE_AIRPLANES a
            WHERE a.$COLUMN_ID NOT IN (
                SELECT f.airplane_id FROM flights f
                WHERE (f.departure_time BETWEEN ? AND ?)
                OR (f.arrival_time BETWEEN ? AND ?)
                OR (? BETWEEN f.departure_time AND f.arrival_time)
                OR (? BETWEEN f.departure_time AND f.arrival_time)
            )
            ORDER BY a.$COLUMN_MODEL ASC
        """.trimIndent()

        val airplanes = mutableListOf<Airplane>()
        val cursor = db.rawQuery(
            query,
            arrayOf(
                departureTime.time.toString(),
                arrivalTime.time.toString(),
                departureTime.time.toString(),
                arrivalTime.time.toString(),
                departureTime.time.toString(),
                arrivalTime.time.toString()
            )
        )
        cursor.use {
            while (it.moveToNext()) {
                airplanes.add(cursorToAirplane(it))
            }
        }
        return airplanes
    }

    private fun cursorToAirplane(cursor: Cursor): Airplane {
        return Airplane(
            id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)),
            model = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MODEL)),
            capacity = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CAPACITY))
        )
    }
} 