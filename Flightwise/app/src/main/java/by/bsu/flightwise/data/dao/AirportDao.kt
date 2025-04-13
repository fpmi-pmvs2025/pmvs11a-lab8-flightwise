package by.bsu.flightwise.data.dao

import by.bsu.flightwise.data.entity.Airport

interface AirportDao {
    fun insert(airport: Airport): Long
    fun getById(id: Long): Airport?
    fun update(airport: Airport): Int
    fun delete(id: Long): Int
    fun getAll(): List<Airport>
    
    fun findByCode(code: String): Airport?
    fun findByCity(city: String): List<Airport>
    fun findByCountry(country: String): List<Airport>
    fun searchByName(query: String): List<Airport>
} 