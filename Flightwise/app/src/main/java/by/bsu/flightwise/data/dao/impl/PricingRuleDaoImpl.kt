package by.bsu.flightwise.data.dao.impl

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import by.bsu.flightwise.data.dao.PricingRuleDao
import by.bsu.flightwise.data.database.DatabaseHelper
import by.bsu.flightwise.data.entity.PricingRule

class PricingRuleDaoImpl(private val db: SQLiteDatabase) : PricingRuleDao {
    companion object {
        private const val TABLE_PRICING_RULES = DatabaseHelper.TABLE_PRICING_RULES
        private const val COLUMN_ID = DatabaseHelper.COLUMN_ID
        private const val COLUMN_NAME = DatabaseHelper.COLUMN_NAME
        private const val COLUMN_FEE = DatabaseHelper.COLUMN_FEE
    }

    override fun insert(pricingRule: PricingRule): Long {
        val values = ContentValues().apply {
            put(COLUMN_NAME, pricingRule.name)
            put(COLUMN_FEE, pricingRule.fee)
        }
        return db.insert(TABLE_PRICING_RULES, null, values)
    }

    override fun getById(id: Long): PricingRule? {
        val cursor = db.query(
            TABLE_PRICING_RULES,
            null,
            "$COLUMN_ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        return cursor.use {
            if (it.moveToFirst()) {
                cursorToPricingRule(it)
            } else {
                null
            }
        }
    }

    override fun update(pricingRule: PricingRule): Int {
        val values = ContentValues().apply {
            put(COLUMN_NAME, pricingRule.name)
            put(COLUMN_FEE, pricingRule.fee)
        }
        return db.update(
            TABLE_PRICING_RULES,
            values,
            "$COLUMN_ID = ?",
            arrayOf(pricingRule.id.toString())
        )
    }

    override fun delete(id: Long): Int {
        return db.delete(
            TABLE_PRICING_RULES,
            "$COLUMN_ID = ?",
            arrayOf(id.toString())
        )
    }

    override fun getAll(): List<PricingRule> {
        val rules = mutableListOf<PricingRule>()
        val cursor = db.query(
            TABLE_PRICING_RULES,
            null,
            null,
            null,
            null,
            null,
            "$COLUMN_NAME ASC"
        )
        cursor.use {
            while (it.moveToNext()) {
                rules.add(cursorToPricingRule(it))
            }
        }
        return rules
    }

    private fun cursorToPricingRule(cursor: Cursor): PricingRule {
        return PricingRule(
            id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)),
            name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
            fee = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_FEE))
        )
    }
} 