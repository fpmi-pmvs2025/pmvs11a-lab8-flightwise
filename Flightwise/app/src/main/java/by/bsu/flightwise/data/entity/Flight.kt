package by.bsu.flightwise.data.entity

import java.util.Date

data class Flight(
    val id: Long = 0,
    val airplaneId: Long,
    val fromAirportId: Long,
    val toAirportId: Long,
    val pricingRuleId: Long,
    val departureTime: Date,
    val arrivalTime: Date
) 