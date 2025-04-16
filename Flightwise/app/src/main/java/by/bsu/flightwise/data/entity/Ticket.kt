package by.bsu.flightwise.data.entity

import java.util.Date
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ticket(
    val id: Long,
    val passengerId: Long,
    val paymentId: Long?,
    val flightId: Long,
    val price: Float,
    val hasLuggage: Boolean,
    val bookedAt: Date,
    val status: TicketStatus
) : Parcelable


enum class TicketStatus {
    PENDING,
    BOOKED,
    CANCELED
}