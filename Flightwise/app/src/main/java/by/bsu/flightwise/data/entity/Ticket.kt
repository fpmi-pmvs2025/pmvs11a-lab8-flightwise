package by.bsu.flightwise.data.entity

import java.util.Date

data class Ticket(
    val id: Long = 0,
    val passengerId: Long,
    val paymentId: Long?,
    val flightId: Long,
    val seatNumber: String,
    val price: Float,
    val hasLuggage: Boolean = false,
    val bookedAt: Date,
    val status: String
)