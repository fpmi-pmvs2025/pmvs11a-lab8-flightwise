package by.bsu.flightwise.data.entity

data class Airport(
    val id: Long = 0,
    val name: String,
    val code: String,
    val city: String,
    val country: String
) 