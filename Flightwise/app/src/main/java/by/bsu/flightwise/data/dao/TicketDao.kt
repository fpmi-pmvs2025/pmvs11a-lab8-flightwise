package by.bsu.flightwise.data.dao

import by.bsu.flightwise.data.entity.Ticket

interface TicketDao {
    fun insert(ticket: Ticket): Long
    fun getById(id: Long): Ticket?
    fun update(ticket: Ticket): Int
    fun delete(id: Long): Int
    fun getAll(): List<Ticket>
    
    fun findByPassengerId(passengerId: Long): List<Ticket>
    fun searchByPassengerName(query: String): List<Ticket>
} 