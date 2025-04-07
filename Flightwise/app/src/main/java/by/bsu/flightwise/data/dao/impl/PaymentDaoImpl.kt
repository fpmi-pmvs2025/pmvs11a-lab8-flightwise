package by.bsu.flightwise.data.dao.impl

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import by.bsu.flightwise.data.dao.PaymentDao
import by.bsu.flightwise.data.database.DatabaseHelper
import by.bsu.flightwise.data.entity.Payment
import java.text.SimpleDateFormat
import java.util.Locale

class PaymentDaoImpl(private val db: SQLiteDatabase) : PaymentDao {
    companion object {
        private const val TABLE_PAYMENTS = DatabaseHelper.TABLE_PAYMENTS
        private const val COLUMN_ID = DatabaseHelper.COLUMN_ID
        private const val COLUMN_USER_ID = DatabaseHelper.COLUMN_USER_ID
        private const val COLUMN_TYPE = DatabaseHelper.COLUMN_TYPE
        private const val COLUMN_DATE = DatabaseHelper.COLUMN_DATE
        
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    }

    override fun insert(payment: Payment): Long {
        val values = ContentValues().apply {
            put(COLUMN_USER_ID, payment.userId)
            put(COLUMN_TYPE, payment.type)
            put(COLUMN_DATE, dateFormat.format(payment.date))
        }
        return db.insert(TABLE_PAYMENTS, null, values)
    }

    override fun getById(id: Long): Payment? {
        val cursor = db.query(
            TABLE_PAYMENTS,
            null,
            "$COLUMN_ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        return cursor.use {
            if (it.moveToFirst()) {
                cursorToPayment(it)
            } else {
                null
            }
        }
    }

    override fun update(payment: Payment): Int {
        val values = ContentValues().apply {
            put(COLUMN_USER_ID, payment.userId)
            put(COLUMN_TYPE, payment.type)
            put(COLUMN_DATE, dateFormat.format(payment.date))
        }
        return db.update(
            TABLE_PAYMENTS,
            values,
            "$COLUMN_ID = ?",
            arrayOf(payment.id.toString())
        )
    }

    override fun delete(id: Long): Int {
        return db.delete(
            TABLE_PAYMENTS,
            "$COLUMN_ID = ?",
            arrayOf(id.toString())
        )
    }

    override fun getAll(): List<Payment> {
        val payments = mutableListOf<Payment>()
        val cursor = db.query(
            TABLE_PAYMENTS,
            null,
            null,
            null,
            null,
            null,
            "$COLUMN_DATE DESC"
        )
        cursor.use {
            while (it.moveToNext()) {
                payments.add(cursorToPayment(it))
            }
        }
        return payments
    }

    private fun cursorToPayment(cursor: Cursor): Payment {
        return Payment(
            id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)),
            userId = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)),
            type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE)),
            date = dateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)))!!,
        )
    }
} 