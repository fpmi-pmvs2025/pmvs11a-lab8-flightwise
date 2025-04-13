package by.bsu.flightwise.data.entity

import java.util.Date

data class Passenger(
    val id: Long = 0,
    val name: String,
    val surname: String,
    val passportNumber: String,
    val birthDate: Date
) 