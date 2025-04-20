package by.bsu.flightwise.data.dao

import by.bsu.flightwise.data.entity.Payment
import java.util.Date

interface PaymentDao {
    fun insert(payment: Payment): Long
    fun getById(id: Long): Payment?
    fun update(payment: Payment): Int
    fun delete(id: Long): Int
    fun getAll(): List<Payment>
} 