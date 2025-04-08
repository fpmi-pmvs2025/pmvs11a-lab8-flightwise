package by.bsu.flightwise.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "flightwise.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_USERS = "users"
        const val TABLE_AIRPLANES = "airplanes"
        const val TABLE_AIRPORTS = "airports"
        const val TABLE_PRICING_RULES = "pricing_rules"
        const val TABLE_FLIGHTS = "flights"
        const val TABLE_PASSENGERS = "passengers"
        const val TABLE_TICKETS = "tickets"
        const val TABLE_PAYMENTS = "payments"

        const val COLUMN_ID = "id"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_PASSWORD_HASH = "password_hash"
        const val COLUMN_NAME = "name"
        const val COLUMN_SURNAME = "surname"
        const val COLUMN_PASSPORT_NUMBER = "passport_number"
        const val COLUMN_BIRTH_DATE = "birth_date"
        const val COLUMN_MODEL = "model"
        const val COLUMN_CAPACITY = "capacity"
        const val COLUMN_CODE = "code"
        const val COLUMN_CITY = "city"
        const val COLUMN_COUNTRY = "country"
        const val COLUMN_TYPE = "type"
        const val COLUMN_FEE = "fee"
        const val COLUMN_AIRPLANE_ID = "airplane_id"
        const val COLUMN_DEPARTURE_AIRPORT_ID = "departure_airport_id"
        const val COLUMN_ARRIVAL_AIRPORT_ID = "arrival_airport_id"
        const val COLUMN_PAYMENT_ID = "payment_id"
        const val COLUMN_DEPARTURE_TIME = "departure_time"
        const val COLUMN_ARRIVAL_TIME = "arrival_time"
        const val COLUMN_PASSENGER_ID = "passenger_id"
        const val COLUMN_FLIGHT_ID = "flight_id"
        const val COLUMN_PRICE = "price"
        const val COLUMN_USER_ID = "user_id"
        const val COLUMN_DATE = "date"
        const val COLUMN_CREATED_AT = "created_at"
        const val COLUMN_PRICING_RULE_ID = "pricing_rule_id"
        const val COLUMN_STATUS = "status"
        const val COLUMN_HAS_LUGGAGE = "has_luggage"
        const val COLUMN_BOOKED_AT = "booked_at"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("PRAGMA foreign_keys = ON")

        db.execSQL("""
            CREATE TABLE $TABLE_USERS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USERNAME VARCHAR(50) NOT NULL UNIQUE,
                $COLUMN_PASSWORD_HASH VARCHAR(255) NOT NULL,
                $COLUMN_CREATED_AT DATETIME DEFAULT CURRENT_TIMESTAMP
            )
        """)

        db.execSQL("""
            CREATE TABLE $TABLE_AIRPLANES (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_MODEL VARCHAR(100) NOT NULL,
                $COLUMN_CAPACITY INTEGER NOT NULL CHECK ($COLUMN_CAPACITY > 0)
            )
        """)

        db.execSQL("""
            CREATE TABLE $TABLE_AIRPORTS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_CODE VARCHAR(10) NOT NULL UNIQUE,
                $COLUMN_CITY VARCHAR(50) NOT NULL,
                $COLUMN_COUNTRY VARCHAR(50) NOT NULL
            )
        """)

        db.execSQL("""
            CREATE TABLE $TABLE_PRICING_RULES (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME VARCHAR(100) NOT NULL,
                $COLUMN_FEE REAL NOT NULL CHECK ($COLUMN_FEE >= 0)
            )
        """)

        db.execSQL("""
            CREATE TABLE $TABLE_FLIGHTS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_AIRPLANE_ID INTEGER NOT NULL,
                $COLUMN_DEPARTURE_AIRPORT_ID INTEGER NOT NULL,
                $COLUMN_ARRIVAL_AIRPORT_ID INTEGER NOT NULL,
                $COLUMN_DEPARTURE_TIME DATETIME NOT NULL,
                $COLUMN_ARRIVAL_TIME DATETIME NOT NULL,
                $COLUMN_PRICING_RULE_ID INTEGER NOT NULL,
                FOREIGN KEY ($COLUMN_AIRPLANE_ID) REFERENCES $TABLE_AIRPLANES($COLUMN_ID),
                FOREIGN KEY ($COLUMN_DEPARTURE_AIRPORT_ID) REFERENCES $TABLE_AIRPORTS($COLUMN_ID),
                FOREIGN KEY ($COLUMN_ARRIVAL_AIRPORT_ID) REFERENCES $TABLE_AIRPORTS($COLUMN_ID),
                FOREIGN KEY ($COLUMN_PRICING_RULE_ID) REFERENCES $TABLE_PRICING_RULES($COLUMN_ID)
            )
        """)

        db.execSQL("""
            CREATE TABLE $TABLE_PASSENGERS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME VARCHAR(50) NOT NULL,
                $COLUMN_SURNAME VARCHAR(50) NOT NULL,
                $COLUMN_PASSPORT_NUMBER VARCHAR(20) NOT NULL UNIQUE,
                $COLUMN_BIRTH_DATE DATE NOT NULL
            )
        """)

        db.execSQL("""
            CREATE TABLE $TABLE_TICKETS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_PASSENGER_ID INTEGER NOT NULL,
                $COLUMN_PAYMENT_ID INTEGER,
                $COLUMN_FLIGHT_ID INTEGER NOT NULL,
                $COLUMN_HAS_LUGGAGE INTEGER NOT NULL DEFAULT 0 CHECK ($COLUMN_HAS_LUGGAGE IN (0, 1)),
                $COLUMN_PRICE REAL NOT NULL CHECK ($COLUMN_PRICE >= 0),
                $COLUMN_BOOKED_AT DATE NOT NULL,
                $COLUMN_STATUS VARCHAR(20) NOT NULL CHECK ($COLUMN_STATUS IN ('PENDING', 'COMPLETED', 'REFUNDED', 'FAILED')),
                FOREIGN KEY ($COLUMN_PASSENGER_ID) REFERENCES $TABLE_PASSENGERS($COLUMN_ID),
                FOREIGN KEY ($COLUMN_FLIGHT_ID) REFERENCES $TABLE_FLIGHTS($COLUMN_ID),
                FOREIGN KEY ($COLUMN_PAYMENT_ID) REFERENCES $TABLE_PAYMENTS($COLUMN_ID)
            )
        """)

        db.execSQL("""
            CREATE TABLE $TABLE_PAYMENTS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USER_ID INTEGER NOT NULL,
                $COLUMN_TYPE VARCHAR(20) NOT NULL CHECK ($COLUMN_TYPE IN ('card', 'cash', 'online')),
                $COLUMN_STATUS VARCHAR(20) NOT NULL DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'BOOKED', 'CANCELED')),
                $COLUMN_DATE DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY ($COLUMN_USER_ID) REFERENCES $TABLE_USERS($COLUMN_ID)
            )
        """)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PAYMENTS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TICKETS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PASSENGERS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_FLIGHTS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PRICING_RULES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_AIRPORTS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_AIRPLANES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    override fun onConfigure(db: SQLiteDatabase) {
        super.onConfigure(db)
        db.setForeignKeyConstraintsEnabled(true)
    }
}
