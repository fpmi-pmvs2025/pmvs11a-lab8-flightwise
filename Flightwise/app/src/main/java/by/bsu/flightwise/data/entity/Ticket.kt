package by.bsu.flightwise.data.entity

import java.util.Date

data class Ticket(
    val id: Long = 0,
    val passengerId: Long,
    val paymentId: Long?,
    val flightId: Long,
    val price: Float,
    val hasLuggage: Boolean = false,
    val bookedAt: Date,
    val status: TicketStatus = TicketStatus.PENDING
)

enum class TicketStatus {
    PENDING,
    BOOKED,
    CANCELED
}