package by.bsu.flightwise.data.dao

import by.bsu.flightwise.data.entity.Passenger
import java.util.Date

interface PassengerDao {
    fun insert(passenger: Passenger): Long
    fun getById(id: Long): Passenger?
    fun update(passenger: Passenger): Int
    fun delete(id: Long): Int
    fun getAll(): List<Passenger>
}