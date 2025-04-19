package by.bsu.flightwise.data.dao.impl

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import by.bsu.flightwise.data.dao.TicketDao
import by.bsu.flightwise.data.database.DatabaseHelper
import by.bsu.flightwise.data.entity.Ticket
import by.bsu.flightwise.data.entity.TicketStatus
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TicketDaoImpl(private val db: SQLiteDatabase) : TicketDao {
    companion object {
        private const val TABLE_TICKETS = DatabaseHelper.TABLE_TICKETS
        private const val COLUMN_ID = DatabaseHelper.COLUMN_ID
        private const val COLUMN_PASSENGER_ID = DatabaseHelper.COLUMN_PASSENGER_ID
        private const val COLUMN_FLIGHT_ID = DatabaseHelper.COLUMN_FLIGHT_ID
        private const val COLUMN_PRICE = DatabaseHelper.COLUMN_PRICE
        private const val COLUMN_PAYMENT_ID = DatabaseHelper.COLUMN_PAYMENT_ID
        private const val COLUMN_HAS_LUGGAGE = DatabaseHelper.COLUMN_HAS_LUGGAGE
        private const val COLUMN_BOOKED_AT = DatabaseHelper.COLUMN_BOOKED_AT
        private  const val COLUMN_STATUS = DatabaseHelper.COLUMN_STATUS
    }

    override fun insert(ticket: Ticket): Long {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val values = ContentValues().apply {
            put(COLUMN_PASSENGER_ID, ticket.passengerId)
            put(COLUMN_FLIGHT_ID, ticket.flightId)
            put(COLUMN_PRICE, ticket.price)
            put(COLUMN_BOOKED_AT, dateFormat.format(Date()))
            put(COLUMN_STATUS, ticket.status.name)
            put(COLUMN_HAS_LUGGAGE, if (ticket.hasLuggage) 1 else 0)
        }
        return db.insert(TABLE_TICKETS, null, values)
    }


    override fun getById(id: Long): Ticket? {
        val cursor = db.query(
            TABLE_TICKETS,
            null,
            "$COLUMN_ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        return cursor.use {
            if (it.moveToFirst()) {
                cursorToTicket(it)
            } else {
                null
            }
        }
    }

    override fun update(ticket: Ticket): Int {
        val values = ContentValues().apply {
            put(COLUMN_PASSENGER_ID, ticket.passengerId)
            put(COLUMN_FLIGHT_ID, ticket.flightId)
            put(COLUMN_PRICE, ticket.price)
        }
        return db.update(
            TABLE_TICKETS,
            values,
            "$COLUMN_ID = ?",
            arrayOf(ticket.id.toString())
        )
    }

    override fun delete(id: Long): Int {
        return db.delete(
            TABLE_TICKETS,
            "$COLUMN_ID = ?",
            arrayOf(id.toString())
        )
    }

    override fun getAll(): List<Ticket> {
        val tickets = mutableListOf<Ticket>()
        val cursor = db.query(
            TABLE_TICKETS,
            null,
            null,
            null,
            null,
            null,
            "$COLUMN_ID ASC"
        )
        cursor.use {
            while (it.moveToNext()) {
                tickets.add(cursorToTicket(it))
            }
        }
        return tickets
    }

    override fun findByPassengerId(passengerId: Long): List<Ticket> {
        val tickets = mutableListOf<Ticket>()
        val cursor = db.query(
            TABLE_TICKETS,
            null,
            "$COLUMN_PASSENGER_ID = ?",
            arrayOf(passengerId.toString()),
            null,
            null,
            "$COLUMN_ID ASC"
        )
        cursor.use {
            while (it.moveToNext()) {
                tickets.add(cursorToTicket(it))
            }
        }
        return tickets
    }

    override fun searchByPassengerName(query: String): List<Ticket> {
        val tickets = mutableListOf<Ticket>()
        val cursor = db.rawQuery("""
            SELECT t.* FROM $TABLE_TICKETS t
            JOIN ${DatabaseHelper.TABLE_PASSENGERS} p ON t.$COLUMN_PASSENGER_ID = p.${DatabaseHelper.COLUMN_ID}
            WHERE p.${DatabaseHelper.COLUMN_NAME} LIKE ? OR p.${DatabaseHelper.COLUMN_SURNAME} LIKE ?
            ORDER BY t.$COLUMN_ID ASC
        """, arrayOf("%$query%", "%$query%"))
        
        cursor.use {
            while (it.moveToNext()) {
                tickets.add(cursorToTicket(it))
            }
        }
        return tickets
    }

    private fun cursorToTicket(cursor: Cursor): Ticket {
        return Ticket(
            id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)),
            passengerId = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_PASSENGER_ID)),
            flightId = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_FLIGHT_ID)),
            price = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_PRICE)),
            paymentId = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_PAYMENT_ID)),
            hasLuggage = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HAS_LUGGAGE)) > 0,
            bookedAt = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOKED_AT))?.let {
                Date(it)
            } ?: Date(),
            status = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATUS))?.let {
                try {
                    TicketStatus.valueOf(it)
                } catch (e: IllegalArgumentException) {
                    TicketStatus.PENDING
                }
            } ?: TicketStatus.PENDING
        )
    }
} 