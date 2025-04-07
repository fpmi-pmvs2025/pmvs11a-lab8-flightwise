package by.bsu.flightwise.data.entity

import java.util.Date

data class User(
    val id: Long = 0,
    val username: String,
    val passwordHash: String,
    val role: String,
    val createdAt: Date
) 