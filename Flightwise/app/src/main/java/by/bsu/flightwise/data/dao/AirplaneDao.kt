package by.bsu.flightwise.data.dao

import by.bsu.flightwise.data.entity.Airplane
import java.util.Date

interface AirplaneDao {
    fun insert(airplane: Airplane): Long
    fun getById(id: Long): Airplane?
    fun update(airplane: Airplane): Int
    fun delete(id: Long): Int
    fun getAll(): List<Airplane>
    
    fun findByCapacityRange(minCapacity: Int, maxCapacity: Int): List<Airplane>
    fun findAvailableForFlight(departureTime: Date, arrivalTime: Date): List<Airplane>
} 