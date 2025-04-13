package by.bsu.flightwise.data.dao

import by.bsu.flightwise.data.entity.PricingRule

interface PricingRuleDao {
    fun insert(pricingRule: PricingRule): Long
    fun getById(id: Long): PricingRule?
    fun update(pricingRule: PricingRule): Int
    fun delete(id: Long): Int
    fun getAll(): List<PricingRule>
} 