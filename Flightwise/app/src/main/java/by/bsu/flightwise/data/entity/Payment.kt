package by.bsu.flightwise.data.entity

import java.util.Date

data class Payment(
    val id: Long = 0,
    val userId: Long,
    val type: String,
    val status: PaymentStatus = PaymentStatus.PENDING,
    val date: Date
)

enum class PaymentStatus {
    PENDING,
    COMPLETED,
    REFUNDED,
    FAILED
} 