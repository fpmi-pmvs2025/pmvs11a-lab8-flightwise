package by.bsu.flightwise.data.dao

import by.bsu.flightwise.data.entity.Flight
import java.util.Date

interface FlightDao {
    fun insert(flight: Flight): Long
    fun getById(id: Long): Flight?
    fun update(flight: Flight): Int
    fun delete(id: Long): Int
    fun getAll(): List<Flight>


    fun findByCitiesAndDateRange(
        departureCity: String,
        arrivalCity: String,
        startDate: Date,
        endDate: Date
    ): List<Flight>

    fun findByCountriesAndDateRange(
        departureCountry: String,
        arrivalCountry: String,
        startDate: Date,
        endDate: Date
    ): List<Flight>
}